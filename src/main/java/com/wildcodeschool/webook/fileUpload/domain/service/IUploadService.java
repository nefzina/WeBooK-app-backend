package com.wildcodeschool.webook.fileUpload.domain.service;

import com.wildcodeschool.webook.fileUpload.domain.dto.MediaDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IUploadService {
    void init();
    MediaDTO store(MultipartFile file);
    Stream<Path> loadAll();
    Path load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();
    String cleanFilename(String originalFilename);

}
