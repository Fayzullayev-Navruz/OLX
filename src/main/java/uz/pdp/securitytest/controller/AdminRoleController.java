package uz.pdp.securitytest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.payload.RoleDTO;
import uz.pdp.securitytest.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class AdminRoleController {

    private final RoleService roleService;

    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping("/one-role")
    public RoleDTO getOneRole(RoleDTO roleDTO) {

        return roleService.search(roleDTO);

    }

    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping()
    public List<RoleDTO> getAllRoles() {

        return roleService.getAllRoles();

    }

    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PostMapping()
    public RoleDTO addRole(@RequestBody RoleDTO roleDTO) {

        return roleService.create(roleDTO);

    }

    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {

        return roleService.update(id,roleDTO);

    }


    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Integer id) {

        roleService.delete(id);

    }




}
