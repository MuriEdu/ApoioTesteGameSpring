package br.ufscar.dc.dsw.gametest.repositories;

import br.ufscar.dc.dsw.gametest.entities.BugEntity;
import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionBugRepository extends CrudRepository<BugEntity, Long> {

    List<BugEntity> findBySession(SessionsEntity session);
    List<BugEntity> findAllByOrderByCreatedAtDesc();
    int countBySession(SessionsEntity session);
}