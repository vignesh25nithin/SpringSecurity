package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="company")
@Getter
@Setter
public class Company {

    private String name;

    @Id
    private String id;
}
