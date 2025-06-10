package uz.pdp.securitytest.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

        private String email;
        private String password;

}
