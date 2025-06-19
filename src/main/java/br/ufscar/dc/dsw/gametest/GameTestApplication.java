package br.ufscar.dc.dsw.gametest;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.gametest.entities.GameEntity;
import br.ufscar.dc.dsw.gametest.entities.GenreEntity;
import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import br.ufscar.dc.dsw.gametest.enums.Roles;
import br.ufscar.dc.dsw.gametest.repositories.GameRepository;
import br.ufscar.dc.dsw.gametest.repositories.GenreRepository;
import br.ufscar.dc.dsw.gametest.repositories.ProjectRepository;
import br.ufscar.dc.dsw.gametest.repositories.UserRepository;
import br.ufscar.dc.dsw.gametest.utils.MockedSessionDependencies;

@SpringBootApplication
public class GameTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameTestApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDefaultUsers(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // Create default admin user
            String adminEmail = "admin@example.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setName("admin");
                admin.setEmail(adminEmail);
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Roles.ADMIN);
                userRepository.save(admin);
                System.out.println("Default ADMIN user created.");
            } else {
                System.out.println("Admin user already exists.");
            }

            // List of default testers
            List<UserEntity> testers = List.of(
                    new UserEntity("Et Bilu", "et.bilu@example.com", encoder.encode("test123"), Roles.TESTER),
                    new UserEntity("Arnold Schwarzenegger", "arnold@example.com", encoder.encode("test123"), Roles.TESTER),
                    new UserEntity("Mickey Mouse", "mickey@example.com", encoder.encode("test123"), Roles.TESTER)
            );

            for (UserEntity tester : testers) {
                if (userRepository.findByEmail(tester.getEmail()).isEmpty()) {
                    userRepository.save(tester);
                    System.out.println("Tester user created: " + tester.getName());
                } else {
                    System.out.println("Tester user already exists: " + tester.getName());
                }
            }
        };
    }

    @Bean
    public CommandLineRunner mockGenresGamesAndProjects(
            GenreRepository genreRepository,
            GameRepository gameRepository,
            ProjectRepository projectRepository,
            MockedSessionDependencies mockedSessionDependencies
    ) {
        return args -> {
            // 1. Gêneros mockados
            List<GenreEntity> genres = List.of(
                    new GenreEntity(null, "Ação", "Jogos com foco em ação e aventura"),
                    new GenreEntity(null, "Estratégia", "Jogos que exigem planejamento"),
                    new GenreEntity(null, "Puzzle", "Jogos de quebra-cabeça e lógica")
            );
            List<GenreEntity> savedGenres = new java.util.ArrayList<>();
            for (GenreEntity genre : genres) {
                GenreEntity existing = genreRepository.findAll().stream()
                        .filter(g -> g.getName().equalsIgnoreCase(genre.getName()))
                        .findFirst().orElse(null);
                if (existing == null) {
                    genre.setId(null);
                    savedGenres.add(genreRepository.save(genre));
                } else {
                    savedGenres.add(existing);
                }
            }

            // 2. Jogos mockados e associação com gêneros
            List<GameEntity> games = List.of(
                    // Jogo 1 e 2: mesmo gênero (para testar filtro)
                    new GameEntity(null, "Game Alpha", "Descrição do Game Alpha", "Empresa A", List.of(savedGenres.get(0))),
                    new GameEntity(null, "Game Beta", "Descrição do Game Beta", "Empresa B", List.of(savedGenres.get(0))),
                    // Jogo 3: dois gêneros
                    new GameEntity(null, "Game Gama", "Descrição do Game Gama", "Empresa C", List.of(savedGenres.get(1), savedGenres.get(2))),
                    // Jogo 4: dois gêneros
                    new GameEntity(null, "Game Delta", "Descrição do Game Delta", "Empresa D", List.of(savedGenres.get(1), savedGenres.get(2)))
            );
            List<GameEntity> savedGames = new java.util.ArrayList<>();
            for (GameEntity game : games) {
                GameEntity existing = gameRepository.findByName(game.getName()).orElse(null);
                if (existing == null) {
                    game.setId(null);
                    savedGames.add(gameRepository.save(game));
                } else {
                    savedGames.add(existing);
                }
            }

            // 3. Projetos mockados, associando aos jogos persistidos
            List<ProjectEntity> projects = mockedSessionDependencies.getRandomProject(savedGames);
            for (ProjectEntity project : projects) {
                boolean exists = projectRepository.findAll().stream()
                        .anyMatch(p -> p.getName().equals(project.getName()));
                if (!exists) {
                    project.setId(null);
                    projectRepository.save(project);
                }
            }

            System.out.println("Gêneros, jogos e projetos mockados inseridos com sucesso.");
        };
    }

    @Bean
    public CommandLineRunner mockStrategies(
            br.ufscar.dc.dsw.gametest.repositories.StrategyRepository strategyRepository,
            MockedSessionDependencies mockedSessionDependencies) {
        return args -> {
            List<br.ufscar.dc.dsw.gametest.entities.StrategyEntity> strategies = mockedSessionDependencies.getStrategies();
            for (br.ufscar.dc.dsw.gametest.entities.StrategyEntity strategy : strategies) {
                // Verifica se já existe uma estratégia com o mesmo nome (ou outro campo único)
                boolean exists = strategyRepository.findAll().stream()
                        .anyMatch(s -> s.getName().equals(strategy.getName()));
                if (!exists) {
                    strategy.setId(null); // Garante que o banco gere o ID
                    strategyRepository.save(strategy);
                }
            }

            System.out.println("Estratégias mockadas inseridas com sucesso.");
        };
    }
}
