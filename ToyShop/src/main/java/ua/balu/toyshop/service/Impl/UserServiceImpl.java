package ua.balu.toyshop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.balu.toyshop.constant.Role;
import ua.balu.toyshop.constant.Status;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.auth.AuthenticationRequest;
import ua.balu.toyshop.dto.user.*;
import ua.balu.toyshop.exception.AlreadyExistException;
import ua.balu.toyshop.exception.DatabaseRepositoryException;
import ua.balu.toyshop.exception.IncorrectInputException;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.City;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.*;
import ua.balu.toyshop.security.JwtTokenProvider;
import ua.balu.toyshop.security.service.EncoderService;
import ua.balu.toyshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private static final String USER_ALREADY_EXIST = "User with email %s already exist";
    private static final String USER_NOT_EXIST_BY_ID = "User with id %s not exist";
    private static final String USER_NOT_EXIST_BY_EMAIL = "User with email %s not exist";
    private static final String WRONG_AUTHENTICATION = "Bad credential";
    private static final String WRONG_TOKEN = "Incorrect token";
    private static final String WRONG_PERSON_UPDATED = "You cant update other user";
    private static final String ROLE_DOESNT_EXIST_BY_NAME = "Role with name %s doesnt exist";
    private static final String STATUS_DOESNT_EXIST = "Status with name %s doesnt exist";
    private static final String ROLE_DOESNT_EXIST_BY_ID = "Role with id %s doesnt exist";
    private static final String STATUS_DOESNT_EXIST_BY_ID = "Status with id %s doesnt exist";
    private static final String CITY_DOESNT_EXIST_BY_ID = "City with id %s doesnt exist";

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final DtoConverter dtoConverter;
    private final RoleRepository roleRepository;
    private final ComplaintRepository complaintRepository;
    private final FeedbackRepository feedbackRepository;
    private final PostRepository postRepository;
    private final StatusRepository statusRepository;
    private final EncoderService encoderService;
    private final CityRepository cityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider, DtoConverter dtoConverter, RoleRepository roleRepository,
                           ComplaintRepository complaintRepository, FeedbackRepository feedbackRepository, PostRepository postRepository, StatusRepository statusRepository, EncoderService encoderService, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.dtoConverter = dtoConverter;
        this.roleRepository = roleRepository;
        this.complaintRepository = complaintRepository;
        this.feedbackRepository = feedbackRepository;
        this.postRepository = postRepository;
        this.statusRepository = statusRepository;
        this.encoderService = encoderService;
        this.cityRepository = cityRepository;
    }

    @Override
    public SuccessRegisteredUser addUser(RegistrationUser registrationUser) {

        if (existUserByEmail(registrationUser.getEmail())) {
            throw new AlreadyExistException(String.format(USER_ALREADY_EXIST, registrationUser.getEmail()));
        }

        City city = cityRepository.findById(registrationUser.getCityId()).orElseThrow(()-> new NotExistException("Not exist city with Id "+registrationUser.getCityId()));

        User user = userRepository.save(
                dtoConverter.convertToEntity(
                        registrationUser,
                        new User())
                                .withCity(city)
                                .withRole(findRole(Role.USER))
                                .withStatus(findStatus(Status.ACTIVE))
                                .withPassword(encoderService.encodePassword(registrationUser.getPassword())));

        log.info("Add user: " + registrationUser.getEmail() + "| With id =" + registrationUser.getId());
        return dtoConverter.convertToDto(user, SuccessRegisteredUser.class);
    }

    @Override
    public SuccessDeletedUser deleteUser(DeleteUser deleteUser, HttpServletRequest request) {
        Long userId = deleteUser.getId();
        if (!userId.equals(getUserFromRequest(request).getId())) {
            throw new IncorrectInputException(WRONG_PERSON_UPDATED);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotExistException(String.format(USER_NOT_EXIST_BY_ID, userId)));
        try {
            complaintRepository.findByUserId(userId).ifPresent(complaintRepository::delete); //TODO переробити зробити щоб вертало всі скарги по юзеру
            feedbackRepository.findByUserId(userId).ifPresent(feedbackRepository::delete); //TODO переробиит щоб вертало всі фідбеки по юзеру
            postRepository.findByUserId(userId);    // TODO Переробити щоб вертало всі пости по бзеру
            userRepository.delete(user);
        } catch (DatabaseRepositoryException e) {
            throw new DatabaseRepositoryException();
        }
        log.info("Successfully user deleted with id " + userId);
        return dtoConverter.convertToDto(user, SuccessDeletedUser.class);
    }

    @Override
    public UserSuccessLogIn authenticateUser(AuthenticationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistException("Didnt found user by email"));
            String token = jwtTokenProvider.createToken(email, user.getRole().getName());

            return new UserSuccessLogIn().withAccessToken(token).withId(user.getId()).withEmail(email);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(WRONG_AUTHENTICATION);
        }
    }

    @Override
    public UserProfile changeUserRole(ChangeUserRole userRole, HttpServletRequest request) {
        long userId = userRole.getUserId();
        ua.balu.toyshop.model.Role role = roleRepository.findById(userRole.getRoleId()).orElseThrow(() -> new NotExistException(String.format(ROLE_DOESNT_EXIST_BY_ID, userRole.getRoleId())));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotExistException(String.format(USER_NOT_EXIST_BY_ID, userId)));
        User userFromRequest = getUserFromRequest(request);
        if (userFromRequest.getRole().getName().equals(Role.ADMIN.getName())
                &&
                !user.getRole().getName().equals(Role.ADMIN.getName())) {

            user.setRole(role);
        }

        return dtoConverter.convertToDto(userRepository.save(user), UserProfile.class);
    }

    @Override
    public SuccessChangedStatus changeUserStatus(ChangeUserStatus userStatus, HttpServletRequest request) {
        String email = userStatus.getEmail();

        ua.balu.toyshop.model.Role role = roleRepository.findById(userStatus.getRoleId()).orElseThrow(() -> new NotExistException(String.format(ROLE_DOESNT_EXIST_BY_ID, userStatus.getRoleId())));
        ua.balu.toyshop.model.Status status = statusRepository.findById(userStatus.getStatusId()).orElseThrow(() -> new NotExistException(String.format(STATUS_DOESNT_EXIST_BY_ID, userStatus.getStatusId())));

        User userToChange = userRepository.findByEmail(email).orElseThrow(() -> new NotExistException(String.format(USER_NOT_EXIST_BY_ID, email)));
        User userFromRequest = getUserFromRequest(request);

        if (userFromRequest.getRole().getName().equals(Role.ADMIN.getName())    //Той хто міняє - адмін
                &&
                !userToChange.getRole().getName().equals((Role.ADMIN.getName()))    // Той кого міняють не адмін
                &&
                !userToChange.getStatus().getName().equals(status.getName())    // Той в кого міняють статус НЕ той САМИЙ статус
        ) {
            userToChange.setStatus(status);
        }

        return dtoConverter.convertToDto(userRepository.save(userToChange), SuccessChangedStatus.class);
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll().stream().map(user -> (UserProfile) dtoConverter.convertToDto(user, UserProfile.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserProfile> getUserByName(String name) {
        return userRepository.findAllByName(name).stream().map(user -> (UserProfile)dtoConverter.convertToDto(user,UserProfile.class)).collect(Collectors.toList());
    }

    //TODO переробити логіку на таку як в UpdatePost якщо не буде зберыгати або створювати новий пост
    @Override
    public SuccessUpdatedUser updateUser(UpdateUser updateProfile, HttpServletRequest request) {
        User userToChange = userRepository.findById(updateProfile.getId()).orElseThrow(()->new IncorrectInputException(String.format(USER_NOT_EXIST_BY_ID,updateProfile.getId())));
        User userFromRequest = getUserFromRequest(request);
        City city = cityRepository.findById(updateProfile.getCityId()).orElseThrow(()->new IncorrectInputException(String.format(CITY_DOESNT_EXIST_BY_ID,updateProfile.getCityId())));
        if (!userToChange.getEmail().equals(userFromRequest.getEmail())) {
            throw new IncorrectInputException(WRONG_PERSON_UPDATED);
        }
        if (!userRepository.existsByEmail(userToChange.getEmail())){
            throw new NotExistException(String.format(USER_NOT_EXIST_BY_EMAIL,userToChange.getEmail()));
        }
        User changedUser = userRepository.save(dtoConverter.convertToEntity(updateProfile,userToChange)
                .withCity(city)
                .withPassword(userToChange.getPassword())
                .withRole(userToChange.getRole())
                .withStatus(userToChange.getStatus()));
        return dtoConverter.convertToDto(changedUser, SuccessUpdatedUser.class);
    }


    public User getUserFromRequest(HttpServletRequest request) {
        return getUserFromToken(jwtTokenProvider.getJwtFromRequest(request));
    }

    public User getUserFromToken(String token) {
        return userRepository.findByEmail(jwtTokenProvider.getEmailFromToken(token)).orElseThrow(() -> new BadCredentialsException(WRONG_TOKEN));
    }

    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public ua.balu.toyshop.model.Role findRole(Role role) {
        return roleRepository.findByName(role.getName()).orElseThrow(() -> new NotExistException(String.format(ROLE_DOESNT_EXIST_BY_NAME, role.getName())));
    }

    public ua.balu.toyshop.model.Status findStatus(Status status) {
        return statusRepository.findByName(status.getName()).orElseThrow(() -> new NotExistException(String.format(STATUS_DOESNT_EXIST, status.getName())));
    }


}
