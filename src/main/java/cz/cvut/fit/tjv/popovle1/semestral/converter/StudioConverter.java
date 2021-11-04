package cz.cvut.fit.tjv.popovle1.semestral.converter;

import cz.cvut.fit.tjv.popovle1.semestral.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;

public class StudioConverter {
    public static Studio fromDTO(StudioDTO studioDTO) {
        return new Studio(322L, studioDTO.getName(), studioDTO.getCountry());
    }

    public static StudioDTO toDTO(Studio studio) {
        return new StudioDTO(studio.getName(), studio.getCountry());
    }
}