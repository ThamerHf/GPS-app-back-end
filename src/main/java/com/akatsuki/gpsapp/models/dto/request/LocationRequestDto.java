package com.akatsuki.gpsapp.models.dto.request;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class LocationRequestDto implements Serializable {

    private  static  final  long serialVersionUID = 5488577048441636916L;

    private Long locationId;

    private String title;

    private String description;

    private String adresse;

    private Point2D.Double coord;
}
