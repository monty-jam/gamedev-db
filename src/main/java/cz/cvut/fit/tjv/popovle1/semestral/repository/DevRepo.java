package cz.cvut.fit.tjv.popovle1.semestral.repository;

import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DevRepo extends CrudRepository<Dev, Long> {
    Optional<Dev> findById(Long id);
}
