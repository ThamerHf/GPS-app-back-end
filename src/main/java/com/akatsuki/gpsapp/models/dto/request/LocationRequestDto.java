package com.akatsuki.gpsapp.models.dto.request;

import com.akatsuki.gpsapp.models.entity.ImageEntity;
import com.akatsuki.gpsapp.models.entity.TagEntity;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "locationRequestDto")
public class LocationRequestDto implements Serializable {

    private  static  final  long serialVersionUID = 5488577048441636916L;

    @Nullable
    private Long locationId;

    private String title;

    private String description;

    @Nullable
    private String adresse;

    @Nullable
    private List<Double> coord = new ArrayList<>();

    @Nullable
    private byte[] image;

    @Nullable
    private List<String> tags = new ArrayList<>();
}
