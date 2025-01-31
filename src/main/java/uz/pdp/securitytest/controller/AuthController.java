//package uz.pdp.securitytest.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import uz.pdp.securitytest.entity.Role;
//import uz.pdp.securitytest.entity.RolePermission;
//import uz.pdp.securitytest.entity.User;
//import uz.pdp.securitytest.enums.PermissionEnum;
//import uz.pdp.securitytest.repository.RolePermissionRepository;
//import uz.pdp.securitytest.repository.RoleRepository;
//import uz.pdp.securitytest.repository.UserRepository;
//import uz.pdp.securitytest.payload.LoginDTO;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:63342")
//public class AuthController {
//    private ObjectMapper objectMapper = new ObjectMapper();
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleRepository roleRepository;
//    private final RolePermissionRepository rolePermissionRepository;
//
//    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.roleRepository = roleRepository;
//        this.rolePermissionRepository = rolePermissionRepository;
//    }
//
//    @PostMapping("/login")
//    public void login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            }
//
//            // Setting authentication context for the logged-in user
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    user,
//                    null,
//                    user.getAuthorities()
//            );
//            securityContext.setAuthentication(authentication);
//
//
//             HttpSession session = request.getSession();
//             session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//
//            response.setStatus(HttpStatus.OK.value());
//            response.getWriter().write(objectMapper.writeValueAsString("Login successful!"));
//        }
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//    }
//
//    @PostMapping("/sign-up")
//    public ResponseEntity<?> signUp(@RequestBody LoginDTO loginDTO) {
//
//        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());
//        if (optionalUser.isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
//        }
//
//        // Encode password
//        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());
//
//        // Get role from the repository
//        Optional<Role> byId = roleRepository.findById(2); // Role ID = 2 (can be changed as needed)
//        Role role = byId.orElseThrow(() -> new RuntimeException("Role not found"));
//
//        // Create RolePermissions
//        RolePermission rolePermission1 = new RolePermission();
//        rolePermission1.setPermission(PermissionEnum.VIEW_CATEGORY);
//        rolePermission1.setRole(role);
//        rolePermissionRepository.save(rolePermission1);
//
//        RolePermission rolePermission2 = new RolePermission();
//        rolePermission2.setPermission(PermissionEnum.VIEW_PRODUCT);
//        rolePermission2.setRole(role);
//        rolePermissionRepository.save(rolePermission2);
//
//        // Create new user and save to repository
//        User user = new User(loginDTO.getUsername(), encodedPassword, role);
//        userRepository.save(user);
//
//        // Set authentication context for the new user
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                user,
//                null,
//                user.getAuthorities()
//        );
//        securityContext.setAuthentication(authentication);
//
//        // Optionally, you can set the authentication in the session
//        // HttpSession session = request.getSession();
//        // session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
//    }
//}
