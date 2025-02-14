package uz.pdp.securitytest.payload;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.securitytest.entity.Role;

@Setter
@Getter
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private RoleDTO roleDTO;

    public UserDto() {
    }
    public UserDto(Integer id, String username, String password, String fullName, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
    }
}
