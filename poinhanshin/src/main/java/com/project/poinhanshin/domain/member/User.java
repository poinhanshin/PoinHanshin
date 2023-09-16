package com.project.poinhanshin.domain.member;

import lombok.Data;

@Data
public class User {

    private Long userno;

    private String id;

    private String password;

    private String name;

    @NotEmpty(message = "필수 값입니다")
    private Integer age;

    @NotEmpty(message = "필수 값입니다")
    private Integer phnum;

    @NotEmpty(message = "필수 값입니다")
    private String mail;

    @NotEmpty(message = "필수 값입니다")
    private String address;


}

