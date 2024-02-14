package com.akatsuki.gpsapp.models.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.sql.Blob;

@Entity
@Data
@Table(name = "image")
public class ImageEntity {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Long imageId;

    private String name;

    private Blob encoding;

    public ImageEntity() {

    }
}
