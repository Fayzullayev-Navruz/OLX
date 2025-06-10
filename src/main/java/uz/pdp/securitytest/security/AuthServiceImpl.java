package uz.pdp.securitytest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.payload.LoginRequest;
import uz.pdp.securitytest.payload.UserDto;
import uz.pdp.securitytest.repository.UserRepository;

import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;






    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byUsername = userRepository.findByEmail(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
else throw new UsernameNotFoundException(username);
    }


    @Override
    public String signIn(LoginRequest signInDTO) {

        User user = userRepository.findByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(signInDTO.getEmail()));

        boolean matches = passwordEncoder.matches(signInDTO.getPassword(), user.getPassword());

        if (!matches)
            throw new BadCredentialsException("Invalid username or password");

        String token = jwtProvider.generateToken(user.getEmail(), new Date(System.currentTimeMillis() + 1000 * 60 * 20));


        return token;

        //token qaytar kelajakda


//        String secret = "7efd2e7d81467f624a7b546e4594375ff1e4735a4cf1486fb99a2133b1098f465133aff6f8fb1e960e728ea49165aa6c73bb3a0e368ef3efb96ecbec79da6194ad1cf8ac3c9770559fd69cd13f4e78c0f10697b46b098a59ed75fc56e45d20698739cb69b314d6769cd27a5c5f7e0b37a39216182098f6e173c136a3d6f581576a7bd9093b6ccc472e500e97ef4ee6a75a12a3e6af75b9fa66823d0225a47f78bae3a1eaefbc995691b0f79f85ce1f5c07deeaf972e887f47d906305bff414cc66044830cf7e40c67bf87200ca30f10cd1eb00b29e523c7e19ca3bc3069f6b51d4c29fdadcc80e4d4f0e12bc7aca7a77fb087d5a24bd2d02ae55153e84584fbd";
//
//        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
//
//        String token = Jwts.builder()
//                .signWith(secretKey)
//                .setSubject(user.getPassword())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*5))
//                .compact();


    }

    @Override
    public Object signUp(UserDto signUpDTO) {

        Optional<User> byUsername = userRepository.findByUsername(signUpDTO.getUsername());

        if (byUsername.isPresent())
            throw new BadCredentialsException("Username is already in use");


        User user = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));


        userRepository.save(user);

        return null;
    }
}
