package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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


}
