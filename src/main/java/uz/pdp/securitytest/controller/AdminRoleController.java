package uz.pdp.securitytest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.payload.RoleDTO;
import uz.pdp.securitytest.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class AdminRoleController {

    private final RoleService roleService;

    @GetMapping("/one-role")
    public RoleDTO getOneRole(RoleDTO roleDTO) {

        return roleService.search(roleDTO);

    }

    @GetMapping()
    public List<RoleDTO> getAllRoles() {

        return roleService.getAllRoles();

    }

    @PostMapping()
    public RoleDTO addRole(@RequestBody RoleDTO roleDTO) {

        return roleService.create(roleDTO);

    }

    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {

            return roleService.update(id,roleDTO);

    }

    @DeleteMapping("/{id}")
    public RoleDTO deleteRole(@PathVariable Integer id) {

     return roleService.delete(id);

    }




}
