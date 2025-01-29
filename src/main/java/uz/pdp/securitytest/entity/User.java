package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.securitytest.enums.Priority;
import uz.pdp.securitytest.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 Created by: Mehrojbek
 DateTime: 15/01/25 21:39
 **/
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String fullName;

    private String phone;
    @Column()
    private uz.pdp.securitytest.enums.Priority priority=Priority.BRONZE;

//    @Enumerated(EnumType.STRING)
//    private RoleEnum role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> permissions = this.role.getRolePermissions().stream()
                .map(RolePermission::getPermission)
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .collect(Collectors.toList());

        return permissions;

        //"ROLE_USER",
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.name());

//        return List.of(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
