package uz.pdp.securitytest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.entity.RolePermission;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.enums.PermissionEnum;
import uz.pdp.securitytest.enums.RoleEnum;
import uz.pdp.securitytest.payload.LoginDTO;
import uz.pdp.securitytest.repository.RolePermissionRepository;
import uz.pdp.securitytest.repository.RoleRepository;
import uz.pdp.securitytest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 Created by: Mehrojbek
 DateTime: 15/01/25 20:37
 **/
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }


    @GetMapping("/login")
    public String loginHtml() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUpHtml() {
        return "sign_up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute LoginDTO loginDTO, HttpServletRequest request) {

        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());
        if (optionalUser.isPresent())
            return "redirect:/auth/login";

        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());


//        Role role = new Role();
//        role.setName("USER"); // Assuming you have a Role entity and setting a role
//        roleRepository.save(role);
        Optional<Role> byId = roleRepository.findById(2);
        Role role = byId.get();
        RolePermission rolePermission = new RolePermission();
        rolePermission.setPermission(PermissionEnum.VIEW_CATEGORY);
        rolePermission.setRole(role);
        rolePermissionRepository.save(rolePermission);

        RolePermission rolePermission2 = new RolePermission();
        rolePermission2.setPermission(PermissionEnum.VIEW_PRODUCT);
        rolePermission2.setRole(role);
        rolePermissionRepository.save(rolePermission2);


        User user = new User(loginDTO.getUsername(),encodedPassword, role);

//        user.setPassword(encodedPassword);
//        user.setUsername(loginDTO.getUsername());
//        user.setRole(role);
//        user.setRole(RoleEnum.USER);

        userRepository.save(user);
        userRepository.flush();


        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession();

        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        System.out.println(user);

        return "redirect:/category";
    }

}
