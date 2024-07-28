package com.wildcodeschool.webook.Auth.domain.dto;

import com.wildcodeschool.webook.Auth.domain.entity.Role;
import com.wildcodeschool.webook.fileUpload.domain.dto.MediaDTO;

public record UserLoginDTO(Number id, Role role, String username, MediaDTO profilePicture) {

}
