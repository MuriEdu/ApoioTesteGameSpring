package br.ufscar.dc.dsw.gametest.entities;

import br.ufscar.dc.dsw.gametest.utils.ListToJsonConverter;
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
    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> images;

    public StrategyEntity(Long id, String name, String description, String tips, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tips = tips;
        this.images = images;
    }

    public StrategyEntity() {

    }

    public Long getId() {
        return id;
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

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
