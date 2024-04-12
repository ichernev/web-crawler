package com.sally.webcrawler.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int searchCount;

    private int dailyCount;

    @ManyToMany(mappedBy = "keywords")
    private List<MyUser> users = new ArrayList<>();

}
