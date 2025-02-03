package uz.pdp.securitytest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.securitytest.enums.PermissionEnum;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDTO {

    private Integer id;
    private String name;

    private List<PermissionEnum> permissions;


}
