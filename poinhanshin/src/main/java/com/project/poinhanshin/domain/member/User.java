package com.project.poinhanshin.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class User {

    private Long userno;

    private String id;

    private String password;

    private String name;

    private Integer age;

    private String phnum;

    private String mail;

    private String address;
}

