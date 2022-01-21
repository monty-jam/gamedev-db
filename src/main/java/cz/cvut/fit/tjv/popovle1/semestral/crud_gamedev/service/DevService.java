package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter.DevConverter;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class DevService {

    @Autowired
    private DevRepo devRepo;
    @Autowired
    private StudioRepo studioRepo;

    public DevDTO create(DevDTO devDTO) throws Exception {
        if (devDTO.getStudioId() != null) {
            Optional<Studio> optStudio = studioRepo.findById(devDTO.getStudioId());
            if (optStudio.isEmpty())
                throw new NotFoundException("This studio is not found.");
            return DevConverter.toDTO(devRepo.save(new Dev(devDTO.getName(), devDTO.getSurname(),
                    devDTO.getSpecialization(),
                    optStudio.get())));
        } else {
            return DevConverter.toDTO(devRepo.save(new Dev(devDTO.getName(), devDTO.getSurname(),
                    devDTO.getSpecialization(),
                    null)));
        }
    }

    public DevDTO read(Integer id) throws Exception {
        if (devRepo.findById(id).isEmpty()) {
            throw new NotFoundException("This developer is not found.");
        }
        return DevConverter.toDTO(devRepo.findById(id).get());
    }

    public Collection<Dev> readAll() {
        return devRepo.findAll();
    }

    public DevDTO update(DevDTO devDTO, Integer id) throws Exception {
        if (devRepo.findById(id).isEmpty()) {
            throw new NotFoundException("This developer is not found.");
        }

        Dev dev = devRepo.findById(id).get();
        dev.setName(devDTO.getName());
        dev.setSurname(devDTO.getSurname());
        dev.setSpecialization(devDTO.getSpecialization());

        if (devDTO.getStudioId() != null) {
            Optional<Studio> optStudio = studioRepo.findById(devDTO.getStudioId());
            if (optStudio.isEmpty())
                throw new NotFoundException("This studio is not found.");

            dev.setStudio(optStudio.get());
        } else {
            dev.setStudio(null);
        }

        return DevConverter.toDTO(devRepo.save(dev));
    }

    public void delete(Integer id) throws Exception {
        Optional<Dev> dev = devRepo.findById(id);
        if (dev.isEmpty()) {
            throw new NotFoundException("This developer is not found.");
        }
        if (dev.get().getStudio() != null)
            dev.get().getStudio().getDevs().remove(dev.get());
        devRepo.deleteById(id);
    }

}
