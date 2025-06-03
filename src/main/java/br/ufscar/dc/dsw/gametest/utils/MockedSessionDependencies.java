package br.ufscar.dc.dsw.gametest.utils;

import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class MockedSessionDependencies {
    public List<StrategyEntity> getStrategies() {
        List<StrategyEntity> strategies = new ArrayList<>();

        strategies.add(new StrategyEntity(
                (long) 1,
                "Defesa de Torres",
                "Estratégia baseada na proteção de pontos estratégicos.",
                "Construa torres em gargalos e rotas obrigatórias.",
                Arrays.asList(
                        "https://cdn.example.com/images/tower1.jpg",
                        "https://cdn.example.com/images/tower2.jpg"
                )
        ));

        strategies.add(new StrategyEntity(
                (long) 2,
                "Ataque Relâmpago",
                "Movimentação rápida para surpreender o inimigo.",
                "Utilize unidades leves e rápidas no início da partida.",
                Arrays.asList(
                        "https://cdn.example.com/images/rush1.jpg",
                        "https://cdn.example.com/images/rush2.jpg"
                )
        ));

        strategies.add(new StrategyEntity(
                (long) 3,
                "Controle de Área",
                "Tomar o controle de áreas centrais para dominar o campo.",
                "Concentre-se em regiões elevadas e cruzamentos estratégicos.",
                Arrays.asList(
                        "https://cdn.example.com/images/area1.jpg",
                        "https://cdn.example.com/images/area2.jpg"
                )
        ));

        return strategies;
    }

    public List<ProjectEntity> getRandomProject() {
        List<ProjectEntity> projects = Arrays.asList(
                new ProjectEntity(
                        1,
                        "Projeto Alpha",
                        LocalDateTime.now(),
                        "Primeiro projeto de exemplo",
                        List.of() // assuming no members for simplicity
                ),
                new ProjectEntity(
                        2,
                        "Projeto Beta",
                        LocalDateTime.now(),
                        "Segundo projeto de exemplo",
                        List.of()
                ),
                new ProjectEntity(
                        3,
                        "Projeto Gama",
                        LocalDateTime.now(),
                        "Terceiro projeto de exemplo",
                        List.of()
                )
        );

        return projects;
    }
}
