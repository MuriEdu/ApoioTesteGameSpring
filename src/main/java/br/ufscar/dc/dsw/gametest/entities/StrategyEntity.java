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
}
