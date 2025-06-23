package br.ufscar.dc.dsw.gametest.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity(name = "bug_tb")
public class BugEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private SessionsEntity session;

    @Column(nullable = false)
    private String description;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Getters
    public long getId() { return id; }
    public SessionsEntity getSession() { return session; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setSession(SessionsEntity session) { this.session = session; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}