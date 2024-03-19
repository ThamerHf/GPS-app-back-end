package com.akatsuki.gpsapp.models.dto.response;

import com.akatsuki.gpsapp.models.entity.ImageEntity;
import com.akatsuki.gpsapp.models.entity.TagEntity;
import com.akatsuki.gpsapp.models.entity.TokenEntity;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "locationResponseDto")
public class LocationResponseDto implements Serializable {

    private  static  final  long serialVersionUID = 5227328178904171767L;

    private Long locationId;

    private String title;

    private String description;

    private String adresse;

    private Point2D.Double coord;

    private byte[] image;

    private List<TagEntity> tags = new ArrayList<>();
}
