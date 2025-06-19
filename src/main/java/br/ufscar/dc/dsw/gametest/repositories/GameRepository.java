package br.ufscar.dc.dsw.gametest.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.ufscar.dc.dsw.gametest.dao.IGameDAO;
import br.ufscar.dc.dsw.gametest.entities.GameEntity;

@Repository
public class GameRepository {

    private final IGameDAO gameDAO;

    public GameRepository(IGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public Optional<GameEntity> findById(Long id) {
        return gameDAO.findById(id);
    }

    public Optional<GameEntity> findByName(String name) {
        return gameDAO.findAll().stream()
                .filter(game -> game.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<GameEntity> findAll() {
        return gameDAO.findAll();
    }

    public GameEntity save(GameEntity game) {
        return gameDAO.save(game);
    }

    public void deleteById(Long id) {
        gameDAO.deleteById(id);
    }

    public List<GameEntity> findByGenres_Id(Long genreId) {
        return gameDAO.findAll().stream()
                .filter(game -> game.getGenres() != null && game.getGenres().stream().anyMatch(g -> g.getId().equals(genreId)))
                .collect(Collectors.toList());
    }

}