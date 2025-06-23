package br.ufscar.dc.dsw.gametest.repositories;

import br.ufscar.dc.dsw.gametest.dao.IBugDAO;
import br.ufscar.dc.dsw.gametest.entities.BugEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BugRepository {

    private final IBugDAO bugDAO;

    public BugRepository(IBugDAO bugDAO) {
        this.bugDAO = bugDAO;
    }

    public Optional<BugEntity> findById(Long id) {
        return bugDAO.findById(id);
    }

    public List<BugEntity> findAll() {
        return bugDAO.findAll();
    }

    public List<BugEntity> findBySessionId(Long sessionId) {
        return bugDAO.findBySessionId(sessionId);
    }

    public BugEntity save(BugEntity bug) {
        return bugDAO.save(bug);
    }

    public void deleteById(Long id) {
        bugDAO.deleteById(id);
    }
}