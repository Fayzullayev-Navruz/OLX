package uz.pdp.securitytest.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.entity.RolePermission;
import uz.pdp.securitytest.enums.PermissionEnum;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {



//    @Query(value = "select  rp.permission from  role_permission rp where rp.role.id=: role_id")
//    List<PermissionEnum> myPermissionsByRoleId(Integer role);

    //    @Transactional
    @Query(value = "select r from role_permission r where r.role.id = :role1")
    void deleteMyRolePermission(Integer role1);

    void deleteAllByRole(Role role);

    List<RolePermission>findAllByRole(Role role);

    RolePermission findByRole(Role role);

//    @Query(value = "delete  from role_permission where role_permission.role.id =: id")
//    void deleteByRoleId(Integer id);
}