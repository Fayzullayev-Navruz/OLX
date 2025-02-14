package uz.pdp.securitytest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.exception.RestException;
import uz.pdp.securitytest.mapper.RoleMapper;
import uz.pdp.securitytest.mapper.UserMapper;
import uz.pdp.securitytest.payload.RoleDTO;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.repository.RoleRepository;
import uz.pdp.securitytest.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;


    @Override
    public List<UserDto> getSimpleUsers() {

        List<User> users = userRepository.findAllByRoleIsNull();
        return userMapper.toUserDto(users);


    }

    @Override
    public List<RoleDTO> getRoles() {


        return roleService.getAllRoles();


    }

    @Override
    public List<UserDto> getSpecificUsers() {

        List<User> myUsers = userRepository.findMyUsers();

        return userMapper.toUserDto(myUsers);
    }


    @Override
    public UserDto appointRole(Integer userId, UserDto userDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> RestException.notFound("User not found ", userId));

        Role role = roleRepository.findById(userDto.getId()).orElseThrow(() -> RestException.notFound("Role not found ", userDto.getId()));

        user.setRole(role);

        User save = userRepository.save(user);

        UserDto userDto1 = userMapper.toUserDto(save);

        userDto1.setRoleDTO(roleMapper.toRoleDTO(role));
        return userDto1;

    }

    @Override
    public UserDto update(Integer userId, UserDto userDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> RestException.notFound("User not found ", userId));

        Optional<Role> id = roleRepository.findById(userDto.getRoleDTO().getId());
        if (id.isPresent()) {
            Role role = id.get();
            user.setRole(role);
            userRepository.save(user);
            User save = userRepository.save(user);
            UserDto userDto1 = userMapper.toUserDto(save);
            userDto1.setRoleDTO(roleMapper.toRoleDTO(role));
            return userDto1;
        }

        throw RestException.notFound("Role not found ", id);

    }

    @Override
    public UserDto delete(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> RestException.notFound("User not found", id));
        userRepository.delete(user);
        return userMapper.toUserDto(user);

    }
}
