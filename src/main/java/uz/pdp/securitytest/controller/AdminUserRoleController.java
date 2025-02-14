package uz.pdp.securitytest.controller;

import lombok.RequiredArgsConstructor;
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




    @GetMapping("/role")
    public List<RoleDTO> getUserRoles() {

        return userRoleService.getRoles();
    }


    @GetMapping("/user")
    public List<UserDto> getAllUsers() {

        return userRoleService.getSpecificUsers();

    }

    @GetMapping("/simple-user")
    public List<UserDto> getSimpleUser() {

        return userRoleService.getSimpleUsers();


    }

    @PostMapping("/{userId}")
    public UserDto createUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {

        return userRoleService.appointRole(userId, userDto);

    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {

        return userRoleService.update(userId, userDto);


    }


    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Integer id) {

        return userRoleService.delete(id);

    }


}
