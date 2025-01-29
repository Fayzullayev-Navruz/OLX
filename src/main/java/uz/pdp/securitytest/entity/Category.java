package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.List;


@Entity
public class Category implements Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @OneToOne
    private Attachment attachment;

//    @ToString.Exclude
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Category parent;
//
//    @OneToMany(mappedBy = "parent")
//    private List<Category> children;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
//
//    public Category getParent() {
//        return parent;
//    }
//
//    public void setParent(Category parent) {
//        this.parent = parent;
//    }
//
//    public List<Category> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<Category> children) {
//        this.children = children;
//    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
