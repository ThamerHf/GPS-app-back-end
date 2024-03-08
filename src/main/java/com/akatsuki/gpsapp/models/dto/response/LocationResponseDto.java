package com.akatsuki.gpsapp.models.dto.response;

import com.akatsuki.gpsapp.models.entity.ImageEntity;
import com.akatsuki.gpsapp.models.entity.TagEntity;
import com.akatsuki.gpsapp.models.entity.TokenEntity;
import lombok.Data;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class LocationResponseDto implements Serializable {

    private static final long serialVersionUID = -6201028315870513028L;

    private Long locationId;

    private String title;

    private String description;

    private String adresse;

    private Point2D.Double coord;

    private List<ImageEntity> images = new ArrayList<>();

    private List<TagEntity> tags = new ArrayList<>();

    private List<TokenEntity> tokens = new ArrayList<>();
}
