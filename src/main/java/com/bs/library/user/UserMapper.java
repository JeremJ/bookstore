package com.bs.library.user;

import org.mapstruct.Mapper;

@Mapper

public interface UserMapper {

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

}