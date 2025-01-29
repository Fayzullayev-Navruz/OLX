package uz.pdp.securitytest.payload;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.securitytest.entity.Category;

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
    private Long price;

    private Category category;
    @NotBlank(message = "imageUrl not be null")
    private String imageUrl;


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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
