package uz.pdp.securitytest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.entity.RolePermission;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.enums.PermissionEnum;
import uz.pdp.securitytest.exception.RestException;
import uz.pdp.securitytest.mapper.RoleMapper;
import uz.pdp.securitytest.payload.RoleDTO;
import uz.pdp.securitytest.repository.RolePermissionRepository;
import uz.pdp.securitytest.repository.RoleRepository;
import uz.pdp.securitytest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final RolePermissionRepository rolePermissionRepository;


    @Override
    public RoleDTO search(RoleDTO roleDTO) {

        if (Objects.nonNull(roleDTO.getName())) {


            Optional<Role> role = roleRepository.findByName(roleDTO.getName());
            if (role.isPresent()) {

                List<PermissionEnum> enums = new ArrayList<>();

                List<RolePermission> allByRole = rolePermissionRepository.findAllByRole(role.get());

                for (RolePermission rolePermission : allByRole) {
                    PermissionEnum permission = rolePermission.getPermission();
                    enums.add(permission);
                }


                RoleDTO roleDTO1 = roleMapper.toRoleDTO(role.get());
                roleDTO1.setPermissions(enums);
                return roleDTO1;
            } else throw RestException.notFound("Not found ", roleDTO.getName());
        }


        throw new RuntimeException("Not found role");

    }

    @Override
    public List<RoleDTO> getAllRoles() {

        List<Role> roles = roleRepository.findAll();


        List<RoleDTO> roleDTOs = new ArrayList<>();

        for (Role role : roles) {

            List<PermissionEnum> permissionEnums = new ArrayList<>();

            List<RolePermission> allByRole = rolePermissionRepository.findAllByRole(role);

            for (RolePermission rolePermission : allByRole) {
                PermissionEnum permission = rolePermission.getPermission();
                permissionEnums.add(permission);
            }


            RoleDTO roleDTO = roleMapper.toRoleDTO(role);
            roleDTO.setPermissions(permissionEnums);
            roleDTOs.add(roleDTO);
        }


        return roleDTOs;

    }


    @Override
    public RoleDTO create(RoleDTO roleDTO) {


        if (roleRepository.findByName(roleDTO.getName()).isPresent()) {
            throw RestException.alreadyHave("this role already have -> ", roleDTO.getName());
        }

        Role newRole = roleRepository.save(roleMapper.toRole(roleDTO));

        List<PermissionEnum> permissions = roleDTO.getPermissions();
        for (PermissionEnum permission : permissions) {

            RolePermission rolePermission = new RolePermission(permission);
            rolePermission.setRole(newRole);
            rolePermissionRepository.save(rolePermission);

        }

        return roleDTO;

    }


    @Override
    public RoleDTO update(Integer id, RoleDTO roleDTO) {

        Role role = roleRepository.findById(id).orElseThrow(() -> RestException.notFound("Not found ", id));



        System.out.println(role);


        delete(id);

        create(roleDTO);




        return roleDTO;
    }

    @Override
    public RoleDTO delete(Integer id) {

        Role role = roleRepository.findById(id).orElseThrow(() -> RestException.notFound("Not found ", id));




        RoleDTO roleDTO = roleMapper.toRoleDTO(role);


        rolePermissionRepository.deleteAll(role.getRolePermissions());

        List<User> allByRole = userRepository.findAllByRole(role);
        for (User user : allByRole) {
            user.setRole(null);
            userRepository.save(user);
        }


        roleRepository.delete(role);

//        System.out.println(roleDTO);

        return roleDTO;


    }
}
