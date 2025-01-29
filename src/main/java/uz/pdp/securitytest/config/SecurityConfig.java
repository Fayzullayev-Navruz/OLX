package uz.pdp.securitytest.config;

import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import uz.pdp.securitytest.enums.RoleEnum;

/**
 Created by: Mehrojbek
 DateTime: 15/01/25 20:27
 **/
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final DBUserDetailsService dbUserDetailsService;

    public SecurityConfig(DBUserDetailsService dbUserDetailsService) {
        this.dbUserDetailsService = dbUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(conf -> conf.disable());

        http.userDetailsService(dbUserDetailsService);

        http.authorizeHttpRequests(conf -> conf

                .requestMatchers(HttpMethod.POST,"/shdbchs")
                .permitAll()

                .requestMatchers("/**","/auth/login", "auth/sign-up","/**.html", "/open","/static/**")
                .permitAll()

//                .requestMatchers(HttpMethod.GET, "/product")
//                .hasAnyRole(RoleEnum.MANAGER.name(), RoleEnum.USER.name())

                .anyRequest()
                .authenticated()
        );

        http.formLogin(conf -> conf
                .loginPage("/auth/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/auth/login")
        );

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//
//        String encodedPassword = passwordEncoder.encode("123");
//
//        System.out.println("password: " + encodedPassword);
//
//        UserDetails user = User
//                .withUsername("john")
//                .password(encodedPassword)
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User
//                .withUsername("admin")
//                .password(encodedPassword)
//                .roles("ADMIN")
//                .build();
//
//        UserDetailsService userDetailsService = new InMemoryUserDetailsManager(user, admin);
//        return userDetailsService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {

//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

//    public static void main(String[] args) {
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encode1 = passwordEncoder.encode("123456");
//        String encode2 = passwordEncoder.encode("123456");
//        String encode3 = passwordEncoder.encode("123456");
//
//        System.out.println(passwordEncoder.matches("123456", encode1));
//        System.out.println(passwordEncoder.matches("123456", encode2));
//        System.out.println(passwordEncoder.matches("123456", encode3));
//
//    }

}
