package ua.balu.toyshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.service.UserService;

import javax.annotation.security.RolesAllowed;

/** +
 * @Author RostFromLv
 *
 * Test Controller
 */
@RestController
public class MainPageController implements Api {

    /**
     * Test app configuration doesnt matter
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/greeting")
    public String helloPage(){
        return "RostFromLV, Hi :)";
    }

}
