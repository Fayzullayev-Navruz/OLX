package uz.pdp.securitytest.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer id;
    @NotBlank
    private String name;

    private Integer attachmentId;

    private MultipartFile image;

    private String ImageUrl;

}
