package com.akatsuki.gpsapp.models.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "authenticatedUserResponseDto")
public class AuthenticatedUserResponseDto {

    private  static  final  long serialVersionUID = -9119488525931874397L;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;
}
