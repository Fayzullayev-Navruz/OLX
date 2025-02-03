package uz.pdp.securitytest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.pdp.securitytest.entity.Role;
import uz.pdp.securitytest.payload.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);

    @Mapping(target = "id", ignore = true)
    void updateRole(RoleDTO roleDTO, @MappingTarget Role role);


}
