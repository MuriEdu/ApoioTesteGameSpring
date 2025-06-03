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

    public ProjectEntity(int id, String name, LocalDateTime createdAt, String description, List<UserEntity> members) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.description = description;
        this.members = members;
    }

    public ProjectEntity() {

    }
}
