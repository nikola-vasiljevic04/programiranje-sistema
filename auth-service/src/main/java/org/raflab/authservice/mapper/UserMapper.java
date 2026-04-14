package org.raflab.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.raflab.authservice.dto.UserDTO;
import org.raflab.authservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDTO toResponseDTO(User user);

    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDTO dto);
}
