package br.ufscar.dc.dsw.gametest.dao;

import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISessionDAO extends JpaRepository<SessionsEntity, Long> {
}
