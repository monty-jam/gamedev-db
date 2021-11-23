package cz.cvut.fit.tjv.popovle1.semestral.service;

import cz.cvut.fit.tjv.popovle1.semestral.converter.StudioConverter;
import cz.cvut.fit.tjv.popovle1.semestral.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.repository.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudioService {

    @Autowired
    private StudioRepo studioRepo;

    public StudioDTO create(StudioDTO studioDTO) throws Exception {
        if (studioRepo.findByName(studioDTO.getName()).isPresent()) {
            throw new StudioAlreadyExistsException("Studio with this name already exists.");
        }
        return StudioConverter.toDTO(studioRepo.save(new Studio(studioDTO.getName(), studioDTO.getCountry())));
    }

    public StudioDTO read(Long id) throws Exception {
        if (studioRepo.findById(id).isEmpty()) {
            throw new StudioNotFoundException("This studio is not found.");
        }
        return StudioConverter.toDTO(studioRepo.findById(id).get());
    }

    public Collection<Studio> readAll() {
        return studioRepo.findAll();
    }

    public StudioDTO update(StudioDTO studioDTO, Long id) throws Exception {
        if (studioRepo.findById(id).isEmpty()) {
            throw new StudioNotFoundException("This studio is not found.");
        }
        if (studioRepo.findByName(studioDTO.getName()).isPresent()
            && studioRepo.findByName(studioDTO.getName()).get().getId() != id) {
            throw new StudioAlreadyExistsException("Studio with this name already exists.");
        }
        Studio studio = studioRepo.findById(id).get();
        studio.setName(studioDTO.getName());
        studio.setCountry(studioDTO.getCountry());

        return StudioConverter.toDTO(studioRepo.save(studio));
    }

    public void delete(Long id) throws Exception {
        if (studioRepo.findById(id).isEmpty()) {
            throw new StudioNotFoundException("This studio is not found.");
        }
        studioRepo.deleteById(id);
    }


}