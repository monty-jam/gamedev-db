package cz.cvut.fit.tjv.popovle1.semestral.service;

import cz.cvut.fit.tjv.popovle1.semestral.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.exception.DevAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.DevNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.repository.DevRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DevService {

    @Autowired
    private DevRepo devRepo;

    public Optional<DevDTO> findById(Long id) {
        return DevDTO.toDTO(devRepo.findById(id));
    }

    public DevDTO create(DevDTO devDTO) throws Exception {
        if (devRepo.findById(devDTO.getId()).isPresent()) {
            throw new DevAlreadyExistsException("That developer already exists.");
        }
        return DevDTO.toDTO(devRepo.save(
                new Dev(
                        devDTO.getName(),
                        devDTO.getSurname(),
                        devDTO.getSpecialization()
                )
        ));
    }

    public Optional<DevDTO> read(Long id) throws Exception {
        if (devRepo.findById(id).isEmpty()) {
            throw new DevNotFoundException("That developer is not found.");
        }
        return DevDTO.toDTO(devRepo.findById(id));
    }

    public DevDTO update(DevDTO devDTO, Long id) throws Exception {
        if (devRepo.findById(id).isEmpty()) {
            throw new DevNotFoundException("That developer is not found.");
        }
        Dev dev = devRepo.findById(id).get();
        dev.setName(devDTO.getName());
        dev.setSurname(devDTO.getSurname());
        dev.setSpecialization(devDTO.getSpecialization());
        return DevDTO.toDTO(dev);
    }

    public void delete(Long id) throws Exception {
        if (devRepo.findById(id).isEmpty()) {
            throw new DevNotFoundException("That developer is not found.");
        }
        devRepo.deleteById(id);
    }


}
