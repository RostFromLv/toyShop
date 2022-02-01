package ua.balu.toyshop.service;

import ua.balu.toyshop.dto.role.RoleProfile;
import ua.balu.toyshop.dto.role.RoleResponse;

import java.util.Set;


public interface RoleService {
    /**
     * The method  return all roles
     *
     * @return Set of {@code RoleResponse }
     */
    Set<RoleResponse> getRoles();

    /**
     * The method fot creating new Role
     * @param roleProfile
     * @return {@code RoleResponse}
     */
    RoleResponse addRole(RoleProfile roleProfile);

    /**
     * The method for delete role, return Response about deleted role
     *
     * @param roleProfile
     * @return {@code RoleResponse}
     */
    RoleResponse deleteRole(RoleProfile roleProfile);

    /**
     * The method for get Role of User by Id,return information of Role
     * @param id
     * @return {@code RoleResponse}
     */
    RoleResponse getUserRoleByUserId(Long id);
}
