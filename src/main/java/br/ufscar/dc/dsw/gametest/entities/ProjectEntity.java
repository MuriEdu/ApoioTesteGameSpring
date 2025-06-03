package br.ufscar.dc.dsw.gametest.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "project_tb")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToMany
    private List<UserEntity> members;
}
