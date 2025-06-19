package br.ufscar.dc.dsw.gametest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufscar.dc.dsw.gametest.entities.GameEntity;

public interface IGameDAO extends JpaRepository<GameEntity, Long> {
}