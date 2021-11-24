package cz.cvut.fit.tjv.popovle1.semestral.service;

import cz.cvut.fit.tjv.popovle1.semestral.converter.StudioConverter;
import cz.cvut.fit.tjv.popovle1.semestral.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.exception.DevNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.repository.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudioService {

    @Autowired
    private StudioRepo studioRepo;
    @Autowired
    private DevRepo devRepo;

    public StudioDTO create(StudioDTO studioDTO) throws Exception {
        if (studioRepo.findByName(studioDTO.getName()).isPresent()) {
            throw new StudioAlreadyExistsException("Studio with this name already exists.");
        }

        Studio studio = new Studio(studioDTO.getName(), studioDTO.getCountry());

        List<Dev> devs = null;
        if (studioDTO.getDevsIds() != null) {
            devs = (List<Dev>) devRepo.findAllById(studioDTO.getDevsIds());
            if (devs.size() != studioDTO.getDevsIds().size())
                throw new DevNotFoundException("Some of given devs are not found.");
            for (Dev dev : devs)
                dev.setStudio(studio);
        }

        studio.setDevs(devs);

        return StudioConverter.toDTO(studioRepo.save(studio));
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

        List<Dev> devs = null;
        if (studioDTO.getDevsIds() != null) {
            devs = (List<Dev>) devRepo.findAllById(studioDTO.getDevsIds());
            if (devs.size() != studioDTO.getDevsIds().size())
                throw new DevNotFoundException("Some of given devs are not found.");
        }

        for (Dev dev : studio.getDevs())
            dev.setStudio(null);

        if (devs != null)
            for (Dev dev : devs)
                dev.setStudio(studio);

        for (Dev dev : studio.getDevs())
            dev.setStudio(null);
        studio.setDevs(null);

        studio.setName(studioDTO.getName());
        studio.setCountry(studioDTO.getCountry());
        studio.setDevs(devs);

        return StudioConverter.toDTO(studioRepo.save(studio));
    }

    public void delete(Long id) throws Exception {
        if (studioRepo.findById(id).isEmpty()) {
            throw new StudioNotFoundException("This studio is not found.");
        }
        for (Dev dev : studioRepo.findById(id).get().getDevs())
            dev.setStudio(null);
        studioRepo.deleteById(id);
    }


}