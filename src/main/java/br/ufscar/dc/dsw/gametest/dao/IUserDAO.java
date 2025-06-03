package br.ufscar.dc.dsw.gametest.dao;

import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDAO extends JpaRepository<UserEntity, Long> {

}
