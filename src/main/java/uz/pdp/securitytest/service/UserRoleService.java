package uz.pdp.securitytest.service;

import uz.pdp.securitytest.controller.UserController;
import uz.pdp.securitytest.payload.RoleDTO;
import uz.pdp.securitytest.payload.UserDto;

import java.util.List;


public interface UserRoleService {


    List<UserDto> getSpecificUsers();

    UserDto appointRole(Integer userId, UserDto roleId);

    UserDto delete(Integer id);

    UserDto update(Integer userId, UserDto userDto);

    List<UserDto> getSimpleUsers();

    List<RoleDTO> getRoles();
}
