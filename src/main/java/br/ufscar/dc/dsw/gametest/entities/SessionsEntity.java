package br.ufscar.dc.dsw.gametest.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "session_tb")
public class SessionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private List<UserEntity> members;
    @OneToOne
    private ProjectEntity project;
    @OneToOne
    private StrategyEntity strategy;
    @Column(nullable = false)
    private int time_minutes;
    @Column
    @CreationTimestamp
    private LocalDateTime created_at;
    @Column
    private LocalDateTime started_at;
    @Column
    private LocalDateTime ended_at;

}
