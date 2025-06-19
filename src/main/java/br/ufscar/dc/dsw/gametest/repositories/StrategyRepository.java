package br.ufscar.dc.dsw.gametest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.ufscar.dc.dsw.gametest.dao.IStrategyDAO;
import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;

@Repository
public class StrategyRepository {

    private final IStrategyDAO strategyDAO;

    public StrategyRepository(IStrategyDAO strategyDAO) {
        this.strategyDAO = strategyDAO;
    }

    public Optional<StrategyEntity> findById(Long id) {
        return strategyDAO.findById(id);
    }

    public List<StrategyEntity> findAll() {
        return strategyDAO.findAll();
    }

    public StrategyEntity save(StrategyEntity strategy) {
        return strategyDAO.save(strategy);
    }

    public void deleteById(Long id) {
        strategyDAO.deleteById(id);
    }

    public List<StrategyEntity> findByName(String name) {
        return strategyDAO.findAll().stream()
                .filter(strategy -> strategy.getName() != null && strategy.getName().equalsIgnoreCase(name))
                .toList();
    }
    
}
