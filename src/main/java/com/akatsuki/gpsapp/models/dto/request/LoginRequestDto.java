package com.akatsuki.gpsapp.models.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;


@Data
@JacksonXmlRootElement(localName = "loginRequestDto")
public class LoginRequestDto implements Serializable {

    private  static  final  long serialVersionUID = -201374924900859192L;

    private String userName;

    private String pwd;
}
