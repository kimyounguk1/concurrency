package com.example.demo.domain.distribution.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "distribution_item")
public class DisItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long stock;


}
