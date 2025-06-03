package br.ufscar.dc.dsw.gametest.dao;

import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IStrategyDAO extends JpaRepository<StrategyEntity, Long> {
    @Query("SELECT s FROM strategy_tb s WHERE LOWER(s.name) = LOWER(:name)")
    List<StrategyEntity> findByNameIgnoreCase(@Param("name") String name);

}
