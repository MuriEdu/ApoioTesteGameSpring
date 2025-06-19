package br.ufscar.dc.dsw.gametest.repositories;

import br.ufscar.dc.dsw.gametest.dao.IGenreDAO;
import br.ufscar.dc.dsw.gametest.entities.GenreEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository {

    private final IGenreDAO genreDAO;

    public GenreRepository(IGenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public Optional<GenreEntity> findById(Long id) {
        return genreDAO.findById(id);
    }

    public List<GenreEntity> findAll() {
        return genreDAO.findAll();
    }

    public GenreEntity save(GenreEntity genre) {
        return genreDAO.save(genre);
    }

    public void deleteById(Long id) {
        genreDAO.deleteById(id);
    }
}