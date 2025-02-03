package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@Entity
public class Category implements Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @OneToMany(mappedBy ="category" )
    private List<ChildCategory>childCategories;

    @OneToOne
    private Attachment attachment;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreatedBy
    private Integer createdBy;

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

    public List<ChildCategory> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<ChildCategory> childCategories) {
        this.childCategories = childCategories;
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
