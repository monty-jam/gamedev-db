package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DevRepo extends CrudRepository<Dev, Long> {
    Optional<Dev> findById(Long id);

    Collection<Dev> findAll();

    List<Dev> findByIdIn(List<Long> devsIds);

    List<Dev> findBySpecializationInAndStudioIdNull(List<String> specializations);
}
