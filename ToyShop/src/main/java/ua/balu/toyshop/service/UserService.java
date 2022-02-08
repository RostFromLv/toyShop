package ua.balu.toyshop.service;

import ua.balu.toyshop.dto.auth.AuthenticationRequest;
import ua.balu.toyshop.dto.user.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     * The method for creating user, return successful created user
     *
     * @param user
     * @return {@code SuccessRegisteredUser}
     */
     SuccessRegisteredUser addUser(RegistrationUser user);

    /**
     * The method for delete user, return information of Deleted user( maybe a lot of INFO)
     * @param deleteUser
     * @param request
     * @return {@code SuccessDeletedUser}
     */
     SuccessDeletedUser deleteUser(DeleteUser deleteUser,HttpServletRequest request);


     UserSuccessLogIn authenticateUser(AuthenticationRequest request);

    /**
     * The method for change user role, return  User Profile after success Changed role
     *
     * @param changeUserRole
     * @param request
     * @return {@code UserProfile}
     */
     UserProfile changeUserRole(ChangeUserRole changeUserRole, HttpServletRequest request);

    /**
     * The method for updating user,return information about success updated User
     * @param updateProfile
     * @param request
     * @return {@code SuccessUpdatedUser}
     */
     SuccessUpdatedUser updateUser(UpdateUser updateProfile,HttpServletRequest request);

    /**
     * The method for changing User status, return status after changing
     *
     * @param userStatus
     * @param request
     * @return {@code SuccessChangedStatus}
     */
     SuccessChangedStatus changeUserStatus(ChangeUserStatus userStatus,HttpServletRequest request);

     List<UserProfile> getAllUsers();
     List<UserProfile> getUserByName(String name);
}
