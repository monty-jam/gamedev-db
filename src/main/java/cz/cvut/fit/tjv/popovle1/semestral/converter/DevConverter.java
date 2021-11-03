package cz.cvut.fit.tjv.popovle1.semestral.converter;

import cz.cvut.fit.tjv.popovle1.semestral.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;

public class DevConverter {
    public static Dev fromDTO(DevDTO DevDTO) {
        return new Dev(322L, DevDTO.getName(), DevDTO.getSurname(), DevDTO.getSpecialization());
    }

    public static DevDTO toDTO(Dev Dev) {
        return new DevDTO(Dev.getName(), Dev.getSurname(), Dev.getSpecialization());
    }
}
