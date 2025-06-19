package br.ufscar.dc.dsw.gametest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;

public interface IProjectDAO extends JpaRepository<ProjectEntity, Long> {

    @Query("SELECT p FROM project_tb p JOIN p.members m WHERE m.id = :memberId")
    List<ProjectEntity> findByMemberId(@Param("memberId") Long memberId);

}
