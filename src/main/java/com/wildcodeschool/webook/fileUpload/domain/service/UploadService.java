package com.wildcodeschool.webook.fileUpload.domain.service;

import com.wildcodeschool.webook.fileUpload.application.StorageProperties;
import com.wildcodeschool.webook.fileUpload.domain.dto.MediaDTO;
import com.wildcodeschool.webook.fileUpload.domain.entity.Media;
import com.wildcodeschool.webook.fileUpload.infrastructure.exception.StorageException;
import com.wildcodeschool.webook.fileUpload.infrastructure.exception.UploadedFileNotFoundException;
import com.wildcodeschool.webook.fileUpload.infrastructure.repository.MediaRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class UploadService implements IUploadService {
    private final MediaRepository mediaRepository;
    private final Path rootLocation;
    private final StorageProperties properties;
    private final MediaMapper mediaMapper;

    public UploadService(MediaRepository mediaRepository, StorageProperties properties, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.properties = properties;
        this.mediaMapper = mediaMapper;

        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public MediaDTO store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            String cleanedFilename = this.cleanFilename(file.getOriginalFilename());
            String cleanedUniqueFilename = "";

            // seperate the filename from its extension to insert the uuid in between
            int lastDotIndex = cleanedFilename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                String name = cleanedFilename.substring(0, lastDotIndex);
                String extension = cleanedFilename.substring(lastDotIndex);
                cleanedUniqueFilename = name + UUID.randomUUID().toString() + extension;
            } else {
                // if the file has no extension, return error
                throw new StorageException("Wrong file extension.");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(cleanedUniqueFilename)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory."); //security check
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // Créez et configurez l'objet Media avec le nom de fichier nettoyé, faire DTO
            Media media = new Media();
            media.setFilename(cleanedUniqueFilename);

            return mediaMapper.transformMediaEntityIntoMediaDTO(this.mediaRepository.save(media));
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new UploadedFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new UploadedFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public String cleanFilename(String originalFilename) {
        // Replace spaces & non-alphanumeric characters (omit dots) with overlines
        return originalFilename.replaceAll("[^a-zA-Z0-9.]", "-");
    }
}
