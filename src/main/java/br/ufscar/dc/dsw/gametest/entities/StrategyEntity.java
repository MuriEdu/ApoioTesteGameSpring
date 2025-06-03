package br.ufscar.dc.dsw.gametest.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "strategy_tb")
public class StrategyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column
    private String tips;
    private List<String> images;

    public StrategyEntity(Long id, String name, String description, String tips, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tips = tips;
        this.images = images;
    }
}
