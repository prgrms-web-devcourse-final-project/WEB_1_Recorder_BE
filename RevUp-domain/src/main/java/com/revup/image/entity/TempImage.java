package com.revup.image.entity;

import com.revup.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "temp_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TempImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private TempImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
