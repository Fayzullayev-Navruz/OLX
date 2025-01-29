package uz.pdp.securitytest.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.securitytest.entity.User;

/**
 Created by: Mehrojbek
 DateTime: 15/01/25 19:43
 **/

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

//    @PreAuthorize("hasAnyRole('USER')")//getAuthorities().contains(ROLE_USER)
    @PreAuthorize("hasAnyRole(T(uz.pdp.securitytest.enums.RoleEnum).USER.name(),T(uz.pdp.securitytest.enums.RoleEnum).MANAGER.name())")
    @GetMapping("/product-test")
    public String product(HttpServletRequest request) {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        User principal = (User) authentication.getPrincipal();

        System.out.println(principal);

        return "product";
    }

}
