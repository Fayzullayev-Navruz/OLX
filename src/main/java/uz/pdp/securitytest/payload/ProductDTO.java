package uz.pdp.securitytest.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.entity.ChildCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * Created by: Mehrojbek
 * DateTime: 17/01/25 21:28
 **/
public class ProductDTO {

    private Integer id;
    @NotBlank(message = "name not be null or blank")
    private String name;
    @Positive(message = "price should be positive")
    private Double price;

@JsonIgnore
    private ChildCategory childCategory;
    @NotBlank(message = "imageUrl not be null")
    private String imageUrl;
    private String description;


    private Boolean isActive = false;

    private Boolean is_check = false;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private MultipartFile image;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ChildCategory getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(ChildCategory childCategory) {
        this.childCategory = childCategory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIs_check() {
        return is_check;
    }

    public void setIs_check(Boolean is_check) {
        this.is_check = is_check;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +

                '}';
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
