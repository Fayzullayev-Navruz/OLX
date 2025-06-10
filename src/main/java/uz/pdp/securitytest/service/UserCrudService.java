package uz.pdp.securitytest.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserCrudService {
    private final UserRepository userRepository;

    public UserCrudService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserData() {
        UserDto userDto = new UserDto();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Optional<User> byId = userRepository.findById(user.getId());
        if (byId.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        userDto.setId(byId.get().getId());
        userDto.setUsername(byId.get().getUsername());
        userDto.setPassword(byId.get().getPassword());
        userDto.setFullName(byId.get().getFullName());
        userDto.setPhoneNumber(byId.get().getPhoneNumber());
        userDto.setRoleId(byId.get().getId());
        userDto.setEmail(byId.get().getEmail());
        return userDto;
    }

    public UserDto addUser(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return userDto;
    }

    public UserDto updateUser(UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (userDto.getId()!=null&& !Objects.equals(user.getId(), userDto.getId())) {
            throw new RuntimeException("you are not an User which you want to update data");
        }
//        User user1 = userRepository.findById(userDto.getId()).get();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
        return userDto;
    }

    public UserDto deleteUser(UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (userDto.getId()!=null&& !Objects.equals(user.getId(), userDto.getId())) {
            throw new RuntimeException("you are not an User which you want to delete data");
        }
       // User user = userRepository.findById(userDto.getId()).get();
        userRepository.delete(user);
        return userDto;
    }
}
