package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user")
@Getter
@Setter
public class User {
    @Id
    String id;
    String name;
    String encrypt;
}
