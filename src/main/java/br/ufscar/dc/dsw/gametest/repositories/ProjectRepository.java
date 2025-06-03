package br.ufscar.dc.dsw.gametest.repositories;

import br.ufscar.dc.dsw.gametest.dao.IProjectDAO;
import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {

    private final IProjectDAO projectDAO;

    public ProjectRepository(IProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public Optional<ProjectEntity> findById(Long id) {
        return projectDAO.findById(id);
    }

    public List<ProjectEntity> findAll() {
        return projectDAO.findAll();
    }

    public ProjectEntity save(ProjectEntity project) {
        return projectDAO.save(project);
    }

    public void deleteById(Long id) {
        projectDAO.deleteById(id);
    }

    public List<ProjectEntity> findByMemberId(Long memberId) {
        return projectDAO.findByMemberId(memberId);
    }

}
