package com.akatsuki.gpsapp.models.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenResponseDto implements Serializable {

    private  static  final  long serialVersionUID = 8307194613483131300L;

    private String token;
}
