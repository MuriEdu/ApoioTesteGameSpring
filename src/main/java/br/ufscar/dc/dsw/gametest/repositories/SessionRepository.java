package br.ufscar.dc.dsw.gametest.repositories;

import br.ufscar.dc.dsw.gametest.dao.ISessionDAO;
import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import br.ufscar.dc.dsw.gametest.enums.SessionState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SessionRepository {

    private final ISessionDAO sessionDAO;

    public SessionRepository(ISessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public Optional<SessionsEntity> findById(Long id) {
        return sessionDAO.findById(id);
    }

    public List<SessionsEntity> findAll() {
        return sessionDAO.findAll();
    }

    public SessionsEntity save(SessionsEntity session) {
        return sessionDAO.save(session);
    }

    public void deleteById(Long id) {
        sessionDAO.deleteById(id);
    }

    public List<SessionsEntity> findByStatus(SessionState status) {
        return sessionDAO.findAll().stream()
                .filter(session -> session.getStatus() == status)
                .toList();
    }

    public List<SessionsEntity> findByProjectId(Long projectId) {
        return sessionDAO.findAll().stream()
                .filter(session -> session.getProject() != null && session.getProject().getId() == projectId)
                .toList();
    }
}
