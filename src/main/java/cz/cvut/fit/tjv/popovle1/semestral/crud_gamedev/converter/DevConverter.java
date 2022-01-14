package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;

import java.util.Collection;

public class DevConverter {
//    public static Dev fromDTO(DevDTO devDTO) {
//        return new Dev(322L, devDTO.getName(), devDTO.getSurname(), devDTO.getSpecialization(), devDTO.getStudioId());
//    }

    public static DevDTO toDTO(Dev dev) {
        if (dev.getStudio() == null)
            return new DevDTO(dev.getId(), dev.getName(), dev.getSurname(), dev.getSpecialization(), null);
        else
            return new DevDTO(dev.getId(), dev.getName(), dev.getSurname(), dev.getSpecialization(), dev.getStudio().getId());
    }

    public static Collection<DevDTO> toDTOs(Collection<Dev> devs) {
        return devs.stream().map(DevConverter::toDTO).toList();
    }
}
