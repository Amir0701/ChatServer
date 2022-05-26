package com.lambakean.data.repository;

import com.lambakean.data.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM images img WHERE img.message_id = :messageId")
    Image[] getImagesByMessageId(Long messageId);

}
