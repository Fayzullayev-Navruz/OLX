package uz.pdp.securitytest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/userData")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")

public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public UserDto getUserData() {
        UserDto userDto = new UserDto();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
        Optional<User> byId = userRepository.findById(1);

        userDto.setId(byId.get().getId());
        userDto.setUsername(byId.get().getUsername());
        userDto.setPassword(byId.get().getPassword());
        userDto.setFullName(byId.get().getFullName());
        userDto.setPhone(byId.get().getPhone());
        return userDto;
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return userDto;
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return userDto;
    }

    @DeleteMapping
    public UserDto deleteUser(@RequestBody UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        userRepository.delete(user);
        return userDto;
    }
}