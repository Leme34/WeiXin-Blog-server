package com.lee.pojo;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Role {
    @Id
    private Long id;

    private String name;

}