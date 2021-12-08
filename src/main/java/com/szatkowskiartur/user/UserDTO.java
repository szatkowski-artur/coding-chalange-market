package com.szatkowskiartur.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private Date birthday;

    private String password;






}
