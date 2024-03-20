package com.akatsuki.gpsapp.models.dto.request;

import com.akatsuki.gpsapp.models.entity.TagEntity;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "locationRequestIntermDto")
public class LocationRequestInterDto {
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
    private List<TagEntity> tags = new ArrayList<>();
}
