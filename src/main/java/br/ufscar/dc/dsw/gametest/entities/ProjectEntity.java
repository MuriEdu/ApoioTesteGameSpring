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

    public List<UserEntity> getMembers() {
        return members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }
}
