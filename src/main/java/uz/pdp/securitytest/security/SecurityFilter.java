package uz.pdp.securitytest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.exception.RestException;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AuthService authService;
    ;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String authorization = request.getHeader("Authorization");
        if (!Objects.equals(method, "GET") || Objects.nonNull(authorization)) {
            if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {

                String token = authorization.substring(7);

                String username = jwtProvider.validateToken(token);

                User user = (User) authService.loadUserByUsername(username);


                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);

            } else if (Objects.equals(requestURI, "/users/login") || Objects.equals(requestURI, "/users/register")) {
                filterChain.doFilter(request, response);
            } else {
                throw RestException.notFound("invalid token", request);
            }
        }
        else filterChain.doFilter(request, response);


    }
}
