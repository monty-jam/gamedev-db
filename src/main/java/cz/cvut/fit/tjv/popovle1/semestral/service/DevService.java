package cz.cvut.fit.tjv.popovle1.semestral.service;

import cz.cvut.fit.tjv.popovle1.semestral.converter.DevConverter;
import cz.cvut.fit.tjv.popovle1.semestral.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.exception.DevNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.repository.StudioRepo;
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
                throw new StudioNotFoundException("This studio is not found.");
            return DevConverter.toDTO(devRepo.save(new Dev(devDTO.getName(), devDTO.getSurname(),
                    devDTO.getSpecialization(),
                    optStudio.get())));
        } else {
            return DevConverter.toDTO(devRepo.save(new Dev(devDTO.getName(), devDTO.getSurname(),
                    devDTO.getSpecialization(),
                    null)));
        }
    }

    public DevDTO read(Long id) throws Exception {
        if (devRepo.findById(id).isEmpty()) {
            throw new DevNotFoundException("This developer is not found.");
        }
        return DevConverter.toDTO(devRepo.findById(id).get());
    }

    public Collection<Dev> readAll() {
        return devRepo.findAll();
    }

    public DevDTO update(DevDTO devDTO, Long id) throws Exception {
        if (devRepo.findById(id).isEmpty()) {
            throw new DevNotFoundException("This developer is not found.");
        }

        Dev dev = devRepo.findById(id).get();
        dev.setName(devDTO.getName());
        dev.setSurname(devDTO.getSurname());
        dev.setSpecialization(devDTO.getSpecialization());

        if (devDTO.getStudioId() != null) {
            Optional<Studio> optStudio = studioRepo.findById(devDTO.getStudioId());
            if (optStudio.isEmpty())
                throw new StudioNotFoundException("This studio is not found.");

            dev.setStudio(optStudio.get());
        } else {
            dev.setStudio(null);
        }

        return DevConverter.toDTO(devRepo.save(dev));
    }

    public void delete(Long id) throws Exception {
        Optional<Dev> dev = devRepo.findById(id);
        if (dev.isEmpty()) {
            throw new DevNotFoundException("This developer is not found.");
        }
        if (dev.get().getStudio() != null)
            dev.get().getStudio().getDevs().remove(dev.get());
        devRepo.deleteById(id);
    }

}
