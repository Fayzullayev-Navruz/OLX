package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.securitytest.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {


    Optional<Role> findByName(String name);
}