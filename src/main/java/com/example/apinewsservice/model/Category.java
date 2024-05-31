package com.example.apinewsservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rubric;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<News> newsList;
}
