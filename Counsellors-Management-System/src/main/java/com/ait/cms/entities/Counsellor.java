package com.ait.cms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "counsellor_tbl")
@Getter
@Setter
public class Counsellor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counsellorId;
    private String name;
    private String email;
    private String password;
    private String phone;
    @CreationTimestamp
    private LocalDate createdAt;

}
