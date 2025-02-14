package uz.pdp.securitytest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.payload.RoleDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleDTO roleDTO);
    List<Role> toRole(List<RoleDTO> roleDTOList);


    RoleDTO toRoleDTO(Role role);
    List<RoleDTO> toRoleDTO(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    void updateRole(RoleDTO roleDTO, @MappingTarget Role role);


}
