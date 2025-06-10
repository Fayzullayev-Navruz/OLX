package uz.pdp.securitytest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.securitytest.entity.Role;

import javax.validation.constraints.NegativeOrZero;


//@Setter
//@Getter
@AllArgsConstructor
@NegativeOrZero
@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;

    //    private RoleDTO roleDTO;
    private Integer roleId;
    private String email;

    public UserDto() {
    }
    public UserDto(Integer id, String username, String password, String fullName, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phone;
    }

}
