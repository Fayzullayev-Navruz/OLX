package uz.pdp.securitytest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.securitytest.enums.Priority;

import java.io.Serializable;

/**
 * DTO for {@link uz.pdp.securitytest.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderDto implements Serializable {
    private Integer id;
    private String username;
}