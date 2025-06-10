//package uz.pdp.securitytest.config;
//
//
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.w3c.dom.stylesheets.LinkStyle;
//import uz.pdp.securitytest.entity.Role;
//import uz.pdp.securitytest.enums.PermissionEnum;
//import uz.pdp.securitytest.enums.RoleEnum;
//import uz.pdp.securitytest.repository.RoleRepository;
//
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer {
//    private final RoleRepository roleRepository;
//
//    @PostConstruct
//    public void init() {
//        for (RoleEnum roleEnum : RoleEnum.values()) {
//            if (roleRepository.findByName(roleEnum).isEmpty()) {
//                Role role = new Role();
//                role.setName(RoleEnum.USER.name());
//                List<PermissionEnum> permissions = new ArrayList<>();
//                if (roleEnum == RoleEnum.USER) {
//                    permissions.add(PermissionEnum.VIEW_PRODUCT);
//                    permissions.add(PermissionEnum.CREATE_PRODUCT);
//                    permissions.add(PermissionEnum.EDIT_PRODUCT);
//                    permissions.add(PermissionEnum.DELETE_PRODUCT);
//                } else if (roleEnum == RoleEnum.MANAGER || roleEnum == RoleEnum.ADMIN) {
//                    permissions.add(PermissionEnum.DELETE_PRODUCT);
//                    permissions.add(PermissionEnum.VIEW_CATEGORY);
//                    permissions.add(PermissionEnum.CREATE_CATEGORY);
//                    permissions.add(PermissionEnum.EDIT_CATEGORY);
//                    permissions.add(PermissionEnum.DELETE_CATEGORY);
//                }
//                role.setRolePermissions(permissions);
//                roleRepository.save(role);
//            }
//        }
//    }
//}
