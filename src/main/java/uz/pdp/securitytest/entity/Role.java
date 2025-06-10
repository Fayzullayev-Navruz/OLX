package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 Created by: Mehrojbek
 DateTime: 17/01/25 21:48
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "roles")
public class Role   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true, nullable = false, length = 20)
    private String name;


    public Role(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<RolePermission> rolePermissions;

    public Role(String name, List<RolePermission> rolePermissions) {
        this.name = name;
        this.rolePermissions = rolePermissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
