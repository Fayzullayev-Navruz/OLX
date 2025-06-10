package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.securitytest.enums.Priority;
import uz.pdp.securitytest.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "users")
public class User  implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String fullName;

    private String phoneNumber;
    @Column()
    private uz.pdp.securitytest.enums.Priority priority=Priority.BRONZE;



    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpiry;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    private String email;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

//
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        List<SimpleGrantedAuthority> permissions = this.role.getRolePermissions().stream()
//                .map(RolePermission::getPermission)
//                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
//                .collect(Collectors.toList());
//
//        return permissions;
//
//        //"ROLE_USER",
////        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.name());
//

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> permissions = this.role.getRolePermissions().stream()
                .map(RolePermission::getPermission)
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .collect(Collectors.toList());

        return permissions;
    }

    ////        return List.of(grantedAuthority);
//    }


    public String getPassword() {
        return password;
    }


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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }

    public void setPasswordResetTokenExpiry(LocalDateTime passwordResetTokenExpiry) {
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }
}
