package br.ufscar.dc.dsw.gametest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufscar.dc.dsw.gametest.entities.GenreEntity;

public interface IGenreDAO extends JpaRepository<GenreEntity, Long> {
}