package uz.pdp.securitytest.service;

import uz.pdp.securitytest.payload.RoleDTO;

import java.util.List;

public interface RoleService {


    List<RoleDTO> getAllRoles();


    RoleDTO create(RoleDTO roleDTO);

    RoleDTO search(RoleDTO roleDTO);

    RoleDTO update(Integer id, RoleDTO roleDTO);

    RoleDTO delete(Integer id);
}
