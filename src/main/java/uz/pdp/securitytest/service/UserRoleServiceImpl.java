package uz.pdp.securitytest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.entity.Managers;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.exception.RestException;
import uz.pdp.securitytest.mapper.RoleMapper;
import uz.pdp.securitytest.mapper.UserMapper;
import uz.pdp.securitytest.payload.RoleDTO;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.repository.ManagerRepository;
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
    private final ManagerRepository managerRepository;
    @Override
    public List<UserDto> getSimpleUsers() {

//        List<User> users = userRepository.findAllByRoleIsNull();
        List<User> users = userRepository.findSimpleUsers();
        return userMapper.toUserDto(users);


    }

    @Override
    public List<RoleDTO> getRoles() {


        return roleService.getAllRoles();


    }

    @Override
    public UserDto getOneUser(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);

        if (byUsername.isPresent()) {
            User user = byUsername.get();

            return userMapper.toUserDto(user);

        }
        throw new UsernameNotFoundException("username not found");

    }

    @Override
    public List<UserDto> getSpecificUsers() {

        List<User> myUsers = userRepository.findMyUsers();

        return userMapper.toUserDto(myUsers);
    }


    @Override
    public UserDto appointRole(Integer userId, UserDto userDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> RestException.notFound("user not found ", userId));

        Role role = roleRepository.findById(userDto.getId()).orElseThrow(() -> RestException.notFound("role not found ", userDto.getId()));

        if (!checking(user,role)) {
            throw RestException.alreadyHave("role is already hava in this User ", role.getName());
        }

        user.setRole(role);

        User savedUser = userRepository.save(user);

        Managers managers = new Managers();
        managers.setUser(user);
        managers.setUncheckedAdds(0);
        managerRepository.save(managers);


        UserDto userDto1 = userMapper.toUserDto(savedUser);

        RoleDTO roleDTO = roleMapper.toRoleDTO(role);
        userDto1.setRoleId(roleDTO.getId());



        return userDto1;
    }

    @Override
    public UserDto update(Integer userId, UserDto userDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("jjj"));

        Role role1 = roleRepository.findById(userDto.getRoleId()).orElseThrow(() -> RestException.notFound("role not found ", userDto.getRoleId()));

        if (!checking(user,role1)) {
            throw RestException.alreadyHave("role is already hava in this User ", role1.getName());
        }

        Optional<Role> id = roleRepository.findById(userDto.getRoleId());
        if (id.isPresent()) {
            Role role = id.get();
            user.setRole(role);
            userRepository.save(user);
            User save = userRepository.save(user);
            Managers managers = new Managers();
            managers.setUser(user);
            managers.setUncheckedAdds(0);
            managerRepository.save(managers);
            UserDto userDto1 = userMapper.toUserDto(save);
            RoleDTO roleDTO = roleMapper.toRoleDTO(role);
            userDto1.setRoleId(roleDTO.getId());
            return userDto1;
        }

        throw RestException.notFound("role not found ", userDto.getRoleId());

    }

    @Override
    public UserDto delete(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("jjj"));
        Managers byManagerId = managerRepository.findByManagerId(user.getId());
        managerRepository.delete(byManagerId);
        userRepository.delete(user);
        return userMapper.toUserDto(user);

    }


    private static boolean checking(User user, Role role) {

        return !role.getName().equals(user.getRole().getName());


    }


}
