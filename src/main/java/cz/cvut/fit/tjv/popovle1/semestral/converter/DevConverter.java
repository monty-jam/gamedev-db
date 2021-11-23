package cz.cvut.fit.tjv.popovle1.semestral.converter;

import cz.cvut.fit.tjv.popovle1.semestral.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;

import java.util.Collection;

public class DevConverter {
    public static Dev fromDTO(DevDTO devDTO) {
        return new Dev(322L, devDTO.getName(), devDTO.getSurname(), devDTO.getSpecialization());
    }

    public static DevDTO toDTO(Dev dev) {
        return new DevDTO(dev.getName(), dev.getSurname(), dev.getSpecialization());
    }

    public static Collection<DevDTO> toDTOs(Collection<Dev> devs) {
        return devs.stream().map(DevConverter::toDTO).toList();
    }
}
