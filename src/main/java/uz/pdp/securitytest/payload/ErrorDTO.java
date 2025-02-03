package uz.pdp.securitytest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO {


    private Timestamp timestamp;
    private String message;
    private int status;

}
