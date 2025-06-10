package uz.pdp.securitytest.service;

import jakarta.transaction.Transactional;
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
import uz.pdp.securitytest.utils.RoleName;

import java.util.*;

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
            } else throw new RuntimeException("user not found");
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
            throw RestException.alreadyHave("Role already have", roleDTO.getName());
        }


        List<PermissionEnum> checkPermissions = roleDTO.getPermissions();

        if (hasDuplicate(checkPermissions)) {
            throw RestException.alreadyHave(" Duplicate permission !!!! ", null);
        }


        Role newRole = roleRepository.save(roleMapper.toRole(roleDTO));

        List<PermissionEnum> permissions = roleDTO.getPermissions();
        for (PermissionEnum permission : permissions) {

            RolePermission rolePermission = new RolePermission(permission);
            rolePermission.setRole(newRole);
            rolePermissionRepository.save(rolePermission);

        }


        Integer id = newRole.getId();

        roleDTO.setId(id);

        return roleDTO;

    }


    @Transactional
    @Override
    public RoleDTO update(Integer id, RoleDTO roleDTO) {


        if (roleRepository.findByName(roleDTO.getName()).isPresent()) {
            throw RestException.alreadyHave("Role already have", roleDTO.getName());
        }

        List<PermissionEnum> checkPermissions = roleDTO.getPermissions();

        if (hasDuplicate(checkPermissions)) {
            throw RestException.alreadyHave(" Duplicate permission !!!! ", null);
        }

        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));


        rolePermissionRepository.deleteAllByRole(role);


        roleMapper.updateRole(roleDTO, role);


        List<PermissionEnum> permissions = roleDTO.getPermissions();

        for (PermissionEnum permission : permissions) {
            RolePermission rolePermission = new RolePermission(permission);
            rolePermission.setRole(role);
            rolePermissionRepository.save(rolePermission);
        }


        roleRepository.save(role);

        roleDTO.setId(id);

        return roleDTO;
    }

    @Override
    public void delete(Integer id) {

        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));

        rolePermissionRepository.deleteAll(role.getRolePermissions());

        Optional<Role> role1 = roleRepository.findByName(RoleName.USER_ROLE);
        if (role1.isEmpty()) {
            throw new RuntimeException("role not found");
        }

        List<User> allByRole = userRepository.findAllByRole(role);
        for (User user : allByRole) {
            user.setRole(role1.get());
            userRepository.save(user);
        }


        roleRepository.delete(role);


    }


    public static <E extends Enum<E>> boolean hasDuplicate(List<E> enumList) {
        Set<E> set = new HashSet<>();
        for (E e : enumList) {
            if (!set.add(e)) {
                return true; // Duplicate found
            }
        }
        return false;
    }
}
