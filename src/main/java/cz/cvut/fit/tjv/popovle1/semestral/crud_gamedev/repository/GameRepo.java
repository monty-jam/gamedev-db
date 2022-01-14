package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface GameRepo extends CrudRepository<Game, Long> {
    Optional<Game> findById(Long id);

    Optional<Game> findByName(String name);

    Collection<Game> findAll();
}