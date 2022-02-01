package ua.balu.toyshop.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.UserRepository;


@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private static final String NOT_FOUND_USER_BY_EMAIL = "USer with email: %s NOT FOUND";
    private static final String NOT_FOUND_USER_BY_ID = "USer with id: %s NOT FOUND";

    final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(NOT_FOUND_USER_BY_EMAIL, email)));
        return SecurityUser.create(user);
    }

    public UserDetails loadUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotExistException(String.format(NOT_FOUND_USER_BY_ID, id)));
        return SecurityUser.create(user);
    }

}
