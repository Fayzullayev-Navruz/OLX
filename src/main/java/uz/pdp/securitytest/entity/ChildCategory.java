package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ChildCategory {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private String name;

@ManyToOne
private Category category;
}
