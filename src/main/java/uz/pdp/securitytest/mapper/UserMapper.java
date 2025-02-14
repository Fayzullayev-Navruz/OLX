package uz.pdp.securitytest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.payload.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDto userDto);

    List<User> toUsers(List<UserDto> userDto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);

    @Mapping(target = "id", ignore = true)
    void updateUser(UserDto userDto,  @MappingTarget User user);

}
