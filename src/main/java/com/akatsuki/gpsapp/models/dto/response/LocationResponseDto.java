package com.akatsuki.gpsapp.models.dto.response;

import lombok.Data;

import java.awt.geom.Point2D;
import java.io.Serializable;

@Data
public class LocationResponseDto implements Serializable {

    private static final long serialVersionUID = -6201028315870513028L;

    private Long locationId;

    private String title;

    private String description;

    private String adresse;

    private Point2D.Double coord;
}
