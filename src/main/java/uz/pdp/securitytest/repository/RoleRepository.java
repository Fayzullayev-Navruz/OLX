package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.securitytest.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}