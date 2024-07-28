package com.wildcodeschool.webook.Auth.domain.service;

import com.wildcodeschool.webook.Auth.domain.dto.UserDTO;
import com.wildcodeschool.webook.Auth.domain.dto.UserLoginDTO;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.fileUpload.domain.dto.MediaDTO;
import com.wildcodeschool.webook.fileUpload.domain.service.MediaMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final MediaMapper mediaMapper;

    public UserMapper(MediaMapper mediaMapper) {
        this.mediaMapper = mediaMapper;
    }

    public UserDTO transformUserEntityInUserDTO(User user) {
        return new UserDTO(
                user.getEmail(),
                user.getUsername(),
                user.getZip_code(),
                user.getCity(),
                user.getRole(),
                user.getPreferences(),
                user.getBooks(),
                user.getProfilePicture());
    }

    public UserLoginDTO transformUserEntityInUserLoginDTO(User user) {
        MediaDTO profilePictureDTO = null;
        if (user.getProfilePicture() != null) {
            profilePictureDTO = this.mediaMapper.transformMediaEntityIntoMediaDTO(user.getProfilePicture());
        }
        return new UserLoginDTO(
                user.getId(),
                user.getRole(),
                user.getUsername(),
                profilePictureDTO);
    }
}
