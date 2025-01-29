package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import uz.pdp.securitytest.enums.PermissionEnum;

/**
 Created by: Mehrojbek
 DateTime: 17/01/25 21:48
 **/
@Entity(name = "role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PermissionEnum permission;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public RolePermission() {
    }

    public RolePermission(PermissionEnum permission) {
        this.permission = permission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PermissionEnum getPermission() {
        return permission;
    }

    public void setPermission(PermissionEnum permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "id=" + id +
                ", permission=" + permission +
                ", role=" + role +
                '}';
    }
}
