package com.lee.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
public class Category {
    @Id
    private Long id;

    private String name;

    @Column(name = "user_id")
    private Long userId;

}