package com.wildcodeschool.webook.fileUpload.infrastructure.repository;

import com.wildcodeschool.webook.fileUpload.domain.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
