package uz.pdp.securitytest.controller;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.payload.LoginRequest;
import uz.pdp.securitytest.payload.TokenDTO;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ServletResponse servletResponse;

    @PostMapping("/register")
    public ApiResponse registerUser(@RequestBody UserDto userDto, HttpServletRequest request) throws IOException {
       return  userService.registerUser(userDto,request);

    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest request) {
    return     userService.login(request);

    }
@PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
//        String response = userService.requestPasswordReset(email);
//        return ResponseEntity.ok(response);
//    }

//    @PostMapping("/reset-password/{token}")
//    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestParam String newPassword) {
//        String response = userService.resetPassword(token, newPassword);
//        return ResponseEntity.ok(response);
//    }

//    @PutMapping("/profile")
//    public ApiResponse updateProfile(
//            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
//            Principal principal,
//            @RequestBody User updatedUser) {
//
//        String email = (principal != null) ? principal.getName() : userEmail;
//
//        if (email == null || email.isEmpty()) {
//            return
//        }
//        Optional<User> userOpt = userService.getUserByEmail(email);
//        if (userOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body("Foydalanuvchi topilmadi!");
//        }
//        return userService.updateUser(userOpt.get().getId(), updatedUser);
//
//    }

    @PutMapping("/{id}")
    public ApiResponse updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
       return  userService.updateUser(id, updatedUser);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        String response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/assignRole")
    public ResponseEntity<String> assignRoleToUser(@PathVariable Integer userId, @RequestParam String roleName) {
        String response = userService.assignRoleToUser(userId, roleName);
        return ResponseEntity.ok(response);
    }
}

