package com.wildcodeschool.webook.fileUpload.application;

import com.wildcodeschool.webook.fileUpload.domain.dto.MediaDTO;
import com.wildcodeschool.webook.fileUpload.domain.service.UploadService;
import com.wildcodeschool.webook.fileUpload.infrastructure.exception.UploadedFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    private final UploadService uploadService;

    public FileUploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/uploads/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> readOne(@PathVariable String filename) {
        Resource file = uploadService.loadAsResource(filename);
        if (file == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/uploads")
    public ResponseEntity<MediaDTO> fileUpload(@RequestParam("file") MultipartFile file) {
        MediaDTO media = uploadService.store(file);
        return ResponseEntity.status(201).body(media);
    }

    @ExceptionHandler(UploadedFileNotFoundException.class)
    public ResponseEntity<?> handleUploadedFileNotFound(UploadedFileNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
