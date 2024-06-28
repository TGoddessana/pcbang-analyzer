package com.gdsanadevlog.pcbanganalyzer.auth.domain;


import lombok.*;
import jakarta.persistence.*;


@Entity
@Table(name = "admins")
@Getter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int password;

    @Override
    public String toString() {
        return "Admin{id=" + id + ", name='" + name + "'}";
    }
}