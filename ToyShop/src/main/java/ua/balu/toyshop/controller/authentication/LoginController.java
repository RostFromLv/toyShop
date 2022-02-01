package ua.balu.toyshop.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.balu.toyshop.controller.marker.Auth;
import ua.balu.toyshop.dto.auth.AuthenticationRequest;
import ua.balu.toyshop.dto.user.UserSuccessLogIn;
import ua.balu.toyshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController implements Auth {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserSuccessLogIn login(@RequestBody AuthenticationRequest request){
        return userService.authenticateUser(request);
    }
    @PostMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request,response,null);
    }
}
