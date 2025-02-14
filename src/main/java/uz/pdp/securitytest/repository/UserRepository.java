package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //select * from users where username=:username
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u FROM users u WHERE u.role.name <> 'USER' ")
    List<User> findMyUsers();

    User findByRole(Role role);

    List<User> findAllByRoleIsNull();

    List<User> findAllByRole(Role role);
}
