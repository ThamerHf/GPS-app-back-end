package com.akatsuki.gpsapp.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;

@Data
@JacksonXmlRootElement(localName = "tagRequestDto")
public class TagRequestDto implements Serializable {

    private  static  final  long serialVersionUID = -3267780769407838840L;

    @JsonProperty(namespace = "tag")
    private String tag;
}
