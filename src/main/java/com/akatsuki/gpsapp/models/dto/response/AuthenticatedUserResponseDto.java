package com.akatsuki.gpsapp.models.dto.response;

import lombok.Data;

@Data
public class AuthenticatedUserResponseDto {

    private  static  final  long serialVersionUID = -9119488525931874397L;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;
}
