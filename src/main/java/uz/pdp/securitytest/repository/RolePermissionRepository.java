package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.securitytest.entity.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
}