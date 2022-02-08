package ua.balu.toyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.user.*;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.repository.UserRepository;
import ua.balu.toyshop.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** +
 * Controller for managing for users
 */
@RestController
public class UserController implements Api {

    private final UserService userService;
    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, DtoConverter dtoConverter) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Use this endpoint to create new user,return main information about user
     * @param registrationUser
     * @return SuccessRegisteredUser
     */
    @PostMapping("/user")
    public SuccessRegisteredUser addUser(@Valid @RequestBody RegistrationUser registrationUser) {
        return userService.addUser(registrationUser);
    }

    /** +
     * Use this endpoint to change User Role
     *
     * @param changeUserRoleProfile
     * @param request
     * @return {@code UserProfile}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/user/role")
    public UserProfile changeUserRole(@Valid @RequestBody ChangeUserRole changeUserRoleProfile, HttpServletRequest request) {
        return userService.changeUserRole(changeUserRoleProfile, request);
    }

    /** +
     *  Use this endpoint to CHANGE user status
     *
     * @param userStatus
     * @param request
     * @return {@code SuccessChangedStatus}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/user/status")
    public SuccessChangedStatus changeStatus(@Valid @RequestBody ChangeUserStatus userStatus, HttpServletRequest request) {
        return userService.changeUserStatus(userStatus, request);
    }


    /** +
     * Use this endpoint to get information about all users
     * @return {@code List<UserProfile>}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/all")
    public List<UserProfile> getAllUsers() {
        return  userService.getAllUsers();
    }

    /** +
     * Use this endpoint to change user except email and password
     * @param updateUserprofile
     * @param request
     * @return {@code SuccessUpdatedUser}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    @PutMapping("/user")
    public SuccessUpdatedUser updatedUser(@Valid @RequestBody UpdateUser updateUserprofile, HttpServletRequest request) {
        return userService.updateUser(updateUserprofile, request);
    }

    /**
     * Use this endpoint to delete User
     *
     * @param deleteUserProfile
     * @param request
     * @return {@code SuccessDeletedUser}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/user")
    public SuccessDeletedUser deletedUser(@Valid @RequestBody DeleteUser deleteUserProfile, HttpServletRequest request) {
        return userService.deleteUser(deleteUserProfile, request);
    }//TODO TEST LATER

    /** +
     * Use this endpoint to get User by email
     * @param email
     * @return UserProfile
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public UserProfile getUsersByEmail(@RequestParam(name = "email") String email) {
        return dtoConverter.
                convertToDto(userRepository.findByEmail(email).orElseThrow(NotExistException::new),
                        UserProfile.class);
    }

    /** +
     * Use this endpoint to get User for name
     * @param name
     * @return {@code UserProfile}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/name")
    public List<UserProfile> getUsersByName(@RequestParam(name = "name") String name) {
        return getUsersByName(name);
    }

}
