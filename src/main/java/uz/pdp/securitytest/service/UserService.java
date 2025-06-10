package uz.pdp.securitytest.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.entity.RolePermission;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.enums.PermissionEnum;
import uz.pdp.securitytest.enums.RoleEnum;
import uz.pdp.securitytest.mapper.UserMapper;
import uz.pdp.securitytest.payload.LoginRequest;
import uz.pdp.securitytest.payload.TokenDTO;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.repository.RolePermissionRepository;
import uz.pdp.securitytest.repository.RoleRepository;
import uz.pdp.securitytest.repository.UserRepository;
import uz.pdp.securitytest.security.AuthService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolePermissionRepository rolePermissionRepository;
    private final ObjectMapper objectMapper;
    private final AuthService authService;
    private final UserMapper userMapper;

    public ApiResponse registerUser(UserDto user, HttpServletRequest request) throws IOException {
        if (!user.getEmail().contains("@")) {
            return new ApiResponse<>("xatolik email xato");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ApiResponse<>("email already in use");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            return new ApiResponse<>("username already in use");
        }
        if (!isValidPassword(user.getPassword())) {
            return new ApiResponse<>("password incorrect");
        }
        if (user.getFullName() == null || !user.getFullName().matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")) {
            return new ApiResponse<>("full name is incorrect");
        }
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().matches("^\\+998\\d{9}$")) {
            return new ApiResponse<>("phone number is incorrect");
        }
        User user1 = new User();
        user1.setEmail(user.getEmail());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setFullName(user.getFullName());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setUsername(user.getUsername());


        Role role = new Role();
        // Default rol: USER
        Optional<Role> byName = roleRepository.findByName(RoleEnum.USER.toString());
        if (byName.isEmpty()) {
            role.setName("USER");
            roleRepository.save(role);
        } else {
            role = byName.get();
        }


        RolePermission rolePermission1 = new RolePermission();
        rolePermission1.setPermission(PermissionEnum.CREATE_PRODUCT);
        rolePermission1.setRole(role);
        rolePermissionRepository.save(rolePermission1);

        RolePermission rolePermission2 = new RolePermission();
        rolePermission2.setPermission(PermissionEnum.VIEW_PRODUCT);
        rolePermission2.setRole(role);
        rolePermissionRepository.save(rolePermission2);
        RolePermission rolePermission3 = new RolePermission();
        rolePermission2.setPermission(PermissionEnum.DELETE_PRODUCT);
        rolePermission2.setRole(role);
        rolePermissionRepository.save(rolePermission3);
        RolePermission rolePermission4 = new RolePermission();
        rolePermission2.setPermission(PermissionEnum.EDIT_PRODUCT);
        rolePermission2.setRole(role);
        rolePermissionRepository.save(rolePermission4);
        user1.setRole(role);

        userRepository.save(user1);
        String token = authService.signIn(new LoginRequest(user1.getEmail(), user.getPassword()));
        return new ApiResponse( HttpStatus.OK.toString(), new TokenDTO(token, user1.getRole().getName()));

    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";
        return password.matches(regex);
    }

    public ApiResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
              return new ApiResponse<>("email not found");

        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

             return new ApiResponse<>("password incorrect");
        }
        String token = authService.signIn(request);
        return new ApiResponse(HttpStatus.OK.toString(),new TokenDTO(token, user.getRole().getName().toString()));

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String requestPasswordReset(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "Xato: Email topilmadi!";
        }
        User user = optionalUser.get();
        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        return "Parolni tiklash uchun token: " + resetToken;
    }

    public String resetPassword(String token, String newPassword) {
        Optional<User> optionalUser = userRepository.findByPasswordResetToken(token);
        if (optionalUser.isEmpty()) {
            return "Xato: Token noto‘g‘ri yoki muddati o‘tgan!";
        }
        User user = optionalUser.get();
        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return "Xato: Tokenning muddati o‘tgan!";
        }
        if (!isValidPassword(newPassword)) {
            return "Xato: Yangi parol kuchli emas! Kamida 8 ta belgi, 1 ta harf, 1 ta raqam va raqam/harfdan tashqari belgi bo'lishi kerak!";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        userRepository.save(user);
        return "Parol muvaffaqiyatli o‘zgartirildi!";
    }

    public ApiResponse updateUser(Integer id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Foydalanuvchi topilmadi!");
        }
        User user = userOptional.get();
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank()) {
            if (!updatedUser.getUsername().equals(user.getUsername()) &&
                    userRepository.existsByUsername(updatedUser.getUsername())) {
                return new ApiResponse<>("email already exist");
            }
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
            if (!updatedUser.getEmail().contains("@")) {
                return new ApiResponse<>("email already incorrect");
            }
            if (!updatedUser.getEmail().equals(user.getEmail()) &&
                    userRepository.existsByEmail(updatedUser.getEmail())) {
                return new ApiResponse<>("email already exist");
            }
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            if (!isValidPassword(updatedUser.getPassword())) {
                return new ApiResponse<>("password incorrect");
            }
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (updatedUser.getFullName() != null && !updatedUser.getFullName().isBlank()) {
            if (!updatedUser.getFullName().matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")) {
                return new ApiResponse<>("full name invalid");
            }
            user.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getPhoneNumber() != null && !updatedUser.getPhoneNumber().isBlank()) {
            if (!updatedUser.getPhoneNumber().matches("^\\+998\\d{9}$")) {
                return new ApiResponse<>("phone number invalid");
            }
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        User save = userRepository.save(user);
      return   new ApiResponse<>(HttpStatus.OK.toString(),userMapper.toUserDto(save));

    }

    public String deleteUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return "Foydalanuvchi topilmadi!";
        }
        userRepository.delete(userOptional.get());
        return "Foydalanuvchi muvaffaqiyatli o‘chirildi!";
    }

    public String assignRoleToUser(Integer userId, String roleName) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "Xato: Foydalanuvchi topilmadi!";
        }
        Optional<Role> optionalRole = roleRepository.findByName(String.valueOf(RoleEnum.valueOf(roleName.toUpperCase())));
        if (optionalRole.isEmpty()) {
            return "Xato: Bunday rol mavjud emas!";
        }
        User user = optionalUser.get();
        Role role = optionalRole.get();


        userRepository.save(user);
        return "Foydalanuvchiga rol muvaffaqiyatli tayinlandi!";
    }
}
