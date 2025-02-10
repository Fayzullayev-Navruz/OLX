package uz.pdp.securitytest.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChildCategoryDto {
    private Integer id;
    @NotBlank
    private String name;

    private Integer categoryId;
}
