package com.akatsuki.gpsapp.models.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@JacksonXmlRootElement(localName = "genericResponseDto")
public class GenericResponseDto implements Serializable {

    private  static  final  long serialVersionUID = 1381820185480003259L;

    private String message;

    public GenericResponseDto(String message) {
        this.message = message;
    }

    public GenericResponseDto() {

    }
}
