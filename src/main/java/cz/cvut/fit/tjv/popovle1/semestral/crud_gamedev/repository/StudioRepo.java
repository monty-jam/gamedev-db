package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudioRepo extends CrudRepository<Studio, Integer> {
    Optional<Studio> findById(Integer id);

    Optional<Studio> findByName(String name);

    Collection<Studio> findAll();

    List<Studio> findByIdIn(List<Integer> studiosIds);
}