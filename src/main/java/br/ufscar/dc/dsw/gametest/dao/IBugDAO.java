package br.ufscar.dc.dsw.gametest.dao;


import br.ufscar.dc.dsw.gametest.entities.BugEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBugDAO extends JpaRepository<BugEntity, Long> {
    List<BugEntity> findBySessionId(Long sessionId);
}
