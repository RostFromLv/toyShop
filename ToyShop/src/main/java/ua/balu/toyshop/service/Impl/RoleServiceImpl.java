package ua.balu.toyshop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.role.RoleProfile;
import ua.balu.toyshop.dto.role.RoleResponse;
import ua.balu.toyshop.exception.AlreadyExistException;
import ua.balu.toyshop.exception.DatabaseRepositoryException;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.Role;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.RoleRepository;
import ua.balu.toyshop.repository.UserRepository;
import ua.balu.toyshop.service.RoleService;

import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_DELETE_ESCEPTION = "Can`t delete role %s";
    private static final String ROLE_ALREADY_EXIST = "Role with name %s already exist";
    private static final String ROLE_DOESNT_EXIST = "Role with name %s doesnt exist";
    private static final String USER_DOESNT_EXIST_BY_ID = "USER with id %s doesnt exist";


    final RoleRepository roleRepository;
    final DtoConverter dtoConverter;
    final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, DtoConverter dtoConverter, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.dtoConverter = dtoConverter;
        this.userRepository = userRepository;
    }

    @Override
    public Set<RoleResponse> getRoles() {
        log.debug("Finding roles");

        return roleRepository.findAll().stream()
                .map(role -> (RoleResponse) dtoConverter.ConvertToDto(role, RoleResponse.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleResponse addRole(RoleProfile roleProfile) {
        if (roleExist(roleProfile.getName())) {
            throw new AlreadyExistException(String.format(ROLE_ALREADY_EXIST, roleProfile.getName()));
        }
        return dtoConverter.ConvertToDto(roleRepository.save(dtoConverter.convertToEntity(roleProfile, new Role())), RoleResponse.class);
    }

    @Override
    public RoleResponse deleteRole(RoleProfile roleProfile) {
        Role role = roleRepository.findByName(roleProfile.getName()).orElseThrow(NotExistException::new);
        if (!roleExist(roleProfile.getName())) {
            throw new NotExistException(String.format(ROLE_DOESNT_EXIST, roleProfile.getName()));
        }
        try {
            roleRepository.delete(role);
            roleRepository.flush();
        } catch (DatabaseRepositoryException e) {
            throw new DatabaseRepositoryException(String.format(ROLE_DELETE_ESCEPTION, role.getName()));
        }
        return dtoConverter.ConvertToDto(role, RoleResponse.class);
    }

    @Override
    public RoleResponse getUserRoleByUserId(Long id) {

        User user = userRepository.findById(id).orElseThrow(()-> new NotExistException(String.format(USER_DOESNT_EXIST_BY_ID,id)));
        Role userRole = user.getRole();
        return dtoConverter.ConvertToDto(userRole, RoleResponse.class);
    }


    private boolean roleExist(String role) {
        return roleRepository.findAll().stream()
                .anyMatch(roles -> roles.getName().equals(role));
    }


}
