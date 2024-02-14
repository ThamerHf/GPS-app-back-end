package com.akatsuki.gpsapp.models.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDto implements Serializable {

    private  static  final  long serialVersionUID = -3809018209518913605L;

    private String userName;

    private String lastName;

    private String firstName;

    private String email;

    private String pwd;
}
