package uz.pdp.securitytest.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.pdp.securitytest.payload.LoginRequest;
import uz.pdp.securitytest.payload.UserDto;

public interface AuthService extends UserDetailsService {

@Query("select users from users where users.email=:userName")
    @Override
    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

    String signIn(LoginRequest signInDTO);

    Object signUp(UserDto signUpDTO);
}
