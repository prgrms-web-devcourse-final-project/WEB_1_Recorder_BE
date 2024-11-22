package com.revup.image.repository;

import com.revup.image.entity.TempImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempImageRepository extends JpaRepository<TempImage, Long> {
}
