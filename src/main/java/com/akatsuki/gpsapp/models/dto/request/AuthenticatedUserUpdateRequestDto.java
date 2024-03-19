package com.akatsuki.gpsapp.models.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticatedUserUpdateRequestDto implements Serializable {

    private  static  final  long serialVersionUID = -5630766049153745964L;

    private String firstName;

    private String lastName;

    private String email;

    private String oldPwd;

    private String newPwd;
}
