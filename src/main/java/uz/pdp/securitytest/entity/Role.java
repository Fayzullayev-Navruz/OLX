package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

/**
 Created by: Mehrojbek
 DateTime: 17/01/25 21:48
 **/
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "roles")
public class Role   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<RolePermission> rolePermissions;

    public Role(String name, List<RolePermission> rolePermissions) {
        this.name = name;
        this.rolePermissions = rolePermissions;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
