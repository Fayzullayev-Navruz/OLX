package uz.pdp.securitytest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.payload.RoleDTO;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.service.UserRoleService;

import java.util.List;

@RestController
@RequestMapping("/admin/user-role")
@RequiredArgsConstructor
public class AdminUserRoleController {

    private final UserRoleService userRoleService;


    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping("/search-by-username")
    public UserDto searchByUsername(@RequestParam String username) {
        return userRoleService.getOneUser(username);
    }

    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping("/role")
    public List<RoleDTO> getUserRoles() {

        return userRoleService.getRoles();
    }

    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping("/user")
    public List<UserDto> getAllUsers() {

        return userRoleService.getSpecificUsers();

    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping("/simple-user")
    public List<UserDto> getSimpleUser() {

        return userRoleService.getSimpleUsers();


    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PostMapping("/{userId}")
    public UserDto appointRole(@PathVariable Integer userId, @RequestBody UserDto roleDTO) {

        return userRoleService.appointRole(userId, roleDTO);

    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {

        return userRoleService.update(userId, userDto);


    }

//    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
//    @DeleteMapping("/{id}")
//    public UserDto deleteUser(@PathVariable Integer id) {
//
//        return userRoleService.delete(id);
//
//    }


}