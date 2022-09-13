package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "role")
@Getter
@Setter
public class Role {
    @Id
    String id;
    String name;
}
