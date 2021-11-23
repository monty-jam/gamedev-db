package cz.cvut.fit.tjv.popovle1.semestral.repository;

import cz.cvut.fit.tjv.popovle1.semestral.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface StudioRepo extends CrudRepository<Studio, Long> {
    Optional<Studio> findById(Long id);

    Optional<Studio> findByName(String name);

    Collection<Studio> findAll();
}