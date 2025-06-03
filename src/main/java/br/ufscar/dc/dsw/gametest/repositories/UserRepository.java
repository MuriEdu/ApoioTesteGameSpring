package br.ufscar.dc.dsw.gametest.repositories;

import br.ufscar.dc.dsw.gametest.dao.IUserDAO;
import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public class UserRepository {

    private final IUserDAO userDAO;

    public UserRepository(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userDAO.findAll().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Optional<UserEntity> findByName(String name) {
        return userDAO.findAll().stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<UserEntity> findAll() {
        return userDAO.findAll();
    }

    public UserEntity save(UserEntity user) {
        return userDAO.save(user);
    }

    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }
}

