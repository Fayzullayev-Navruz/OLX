package uz.pdp.securitytest.entity;

import jakarta.persistence.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
public class Product implements Parent{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String name;
    @Positive
    private Long price;

    private String description;

    private Boolean isActive=false;

    private Boolean is_check=false;

    @ManyToOne
    private Category category;
    @OneToOne
    private Attachment attachment;

    @ManyToOne
    private User user;



    public Product(String name, Long price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product(String name, Long price, Category category, Attachment attachment) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.attachment = attachment;
    }

    public Product() {
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

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
