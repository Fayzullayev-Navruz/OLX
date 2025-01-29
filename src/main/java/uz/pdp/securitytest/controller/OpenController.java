package uz.pdp.securitytest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 Created by: Mehrojbek
 DateTime: 17/01/25 19:43
 **/
@RestController
@RequestMapping("/open")
public class OpenController {

    @GetMapping
    public String open() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            System.out.println("authenticated");
        } else {
            System.out.println("Anonim");
        }

        System.out.println(authentication);

        return "open";
    }

}
