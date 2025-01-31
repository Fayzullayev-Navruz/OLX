package uz.pdp.securitytest.payload;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.securitytest.entity.Category;

import javax.validation.constraints.NotBlank;

public class CategoryDto {
    private Integer id;
    @NotBlank
    private String name;

    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public CategoryDto(Integer id, String name, String filePath) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto() {

    }

    public CategoryDto(Category category) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
