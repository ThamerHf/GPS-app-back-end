package com.akatsuki.gpsapp.models.dto.response;

import com.akatsuki.gpsapp.models.entity.TagEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;
import java.util.function.Function;

@Data
@JacksonXmlRootElement(localName = "tagResponseDto")
public class TagResponseDto implements Serializable{

    private  static  final  long serialVersionUID = 798242976076486672L;

    private String tag;

    private int numberOfLocations;


}
