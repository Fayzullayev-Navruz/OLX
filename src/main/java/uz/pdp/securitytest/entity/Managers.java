package uz.pdp.securitytest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Managers implements Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    private Integer uncheckedAdds;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUncheckedAdds() {
        return uncheckedAdds;
    }

    public void setUncheckedAdds(Integer uncheckedAdds) {
        this.uncheckedAdds = uncheckedAdds;
    }
}
