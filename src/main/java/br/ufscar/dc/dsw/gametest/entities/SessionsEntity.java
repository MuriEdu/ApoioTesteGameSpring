package br.ufscar.dc.dsw.gametest.entities;

import br.ufscar.dc.dsw.gametest.enums.SessionState;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "session_tb")
public class SessionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_tb_id")
    private ProjectEntity project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_tb_id")
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionState status;



    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public StrategyEntity getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyEntity strategy) {
        this.strategy = strategy;
    }

    public int getTime_minutes() {
        return time_minutes;
    }

    public void setTime_minutes(int time_minutes) {
        this.time_minutes = time_minutes;
    }

    public SessionState getStatus() {
        return status;
    }

    public void setStatus(SessionState status) {
        this.status = status;
    }

    public LocalDateTime getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(LocalDateTime ended_at) {
        this.ended_at = ended_at;
    }

    public LocalDateTime getStarted_at() {
        return started_at;
    }

    public void setStarted_at(LocalDateTime started_at) {
        this.started_at = started_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }
}
