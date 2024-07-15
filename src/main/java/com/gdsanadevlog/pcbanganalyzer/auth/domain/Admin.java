package com.gdsanadevlog.pcbanganalyzer.auth.domain;


import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "admins")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;


}