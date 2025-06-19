package br.ufscar.dc.dsw.gametest.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.gametest.entities.GameEntity;
import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;

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

    public List<GameEntity> getRandomGames() {
        return Arrays.asList(
            new GameEntity(null, "Game Alpha", "Descrição do Game Alpha", "Empresa A", List.of()),
            new GameEntity(null, "Game Beta", "Descrição do Game Beta", "Empresa B", List.of()),
            new GameEntity(null, "Game Gama", "Descrição do Game Gama", "Empresa C", List.of())
        );
    }

    // Recebe a lista de jogos já persistidos e associa corretamente aos projetos
    public List<ProjectEntity> getRandomProject(List<GameEntity> savedGames) {
        GameEntity game1 = savedGames.get(0);
        GameEntity game2 = savedGames.get(1);
        GameEntity game3 = savedGames.get(2);

        List<ProjectEntity> projects = Arrays.asList(
                new ProjectEntity(
                        null,
                        "Projeto Alpha",
                        LocalDateTime.now(),
                        "Primeiro projeto de exemplo",
                        List.of(),
                        game1
                ),
                new ProjectEntity(
                        null,
                        "Projeto Beta",
                        LocalDateTime.now(),
                        "Segundo projeto de exemplo",
                        List.of(),
                        game2
                ),
                new ProjectEntity(
                        null,
                        "Projeto Gama",
                        LocalDateTime.now(),
                        "Terceiro projeto de exemplo",
                        List.of(),
                        game3
                )
        );

        return projects;
    }
}