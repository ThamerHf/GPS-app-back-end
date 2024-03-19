package com.akatsuki.gpsapp.models.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;

@Data
@JacksonXmlRootElement(localName = "tokenResponseDto")
public class TokenResponseDto implements Serializable {

    private  static  final  long serialVersionUID = 8307194613483131300L;

    private String token;
}
