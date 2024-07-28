package com.wildcodeschool.webook.fileUpload.domain.service;

import com.wildcodeschool.webook.fileUpload.domain.dto.MediaDTO;
import com.wildcodeschool.webook.fileUpload.domain.entity.Media;
import org.springframework.stereotype.Service;

@Service
public class MediaMapper {

    public MediaDTO transformMediaEntityIntoMediaDTO(Media media) {
        return new MediaDTO(
                media.getId(),
                media.getFilename());
    }
}
