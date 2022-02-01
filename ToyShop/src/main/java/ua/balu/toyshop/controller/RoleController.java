package ua.balu.toyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.dto.role.RoleProfile;
import ua.balu.toyshop.dto.role.RoleResponse;
import ua.balu.toyshop.service.RoleService;

import javax.validation.Valid;
import java.util.Set;

/** +
 * Controller for handling Roles
 */
@RestController
public class RoleController implements Api {

    final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /** +
     * Use this endpoint to get all roles
     *
     * @return {@code  Set<RoleResponse>}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles")
    Set<RoleResponse> getAllRoles() {
        return roleService.getRoles();
    }

    /** +
     *  Use this endpoint to add role(need handling for Enum)
     *
     * @param roleProfile
     * @return {@code RoleResponse}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/role")
    RoleResponse addRole(@Valid @RequestBody RoleProfile roleProfile) {
        return roleService.addRole(roleProfile);
    }

    /** +
     * Use this endpoint to delete Role by name value (mistake can move RequestBody to Requestparam)
     * @param roleProfile
     * @return {@code RoleResponse}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/role")
    RoleResponse deleteRoleByName(@Valid @RequestBody RoleProfile roleProfile) {
        return roleService.deleteRole(roleProfile);
    }

    /** +
     * Use this endpoint to get role for certain user
     * @param userId
     * @return {@code RoleResponse}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/role/user")
    RoleResponse getUserRole(@Valid @RequestParam(name = "userId") long userId) {
        return roleService.getUserRoleByUserId(userId);
    }
}
