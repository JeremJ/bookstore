package com.bs.library.mapper;

import com.bs.library.dto.UserDTO;
import com.bs.library.entity.User;
import org.mapstruct.Mapper;

@Mapper

public interface UserMapper {

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

}