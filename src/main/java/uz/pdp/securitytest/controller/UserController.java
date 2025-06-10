package uz.pdp.securitytest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.repository.UserRepository;
import uz.pdp.securitytest.service.UserCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userData")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")

public class UserController {
    private final UserRepository userRepository;
    private final UserCrudService userCrudService;

    public UserController(UserRepository userRepository, UserCrudService userCrudService) {
        this.userRepository = userRepository;
        this.userCrudService = userCrudService;
    }


    @GetMapping
    public ApiResponse getUserData() {
        try {
            UserDto userData = userCrudService.getUserData();
            return new ApiResponse<>(HttpStatus.OK.toString(), userData);
        }catch (Exception e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
        }

    }

    @PostMapping
    public ApiResponse addUser(@RequestBody UserDto userDto) {
        UserDto userDto1 = userCrudService.addUser(userDto);
        return new ApiResponse<>(HttpStatus.OK.toString(), userDto1);
    }

    @PutMapping
    public ApiResponse updateUser(@RequestBody UserDto userDto) {
        try {
            UserDto userDto1 = userCrudService.updateUser(userDto);
            return new ApiResponse<>(HttpStatus.OK.toString(), userDto1);
        }catch (Exception e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
        }

    }

    @DeleteMapping
    public UserDto deleteUser(@RequestBody UserDto userDto) {
    return    userCrudService.deleteUser(userDto);

    }
}