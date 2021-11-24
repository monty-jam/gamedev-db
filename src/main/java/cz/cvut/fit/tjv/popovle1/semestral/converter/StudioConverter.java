package cz.cvut.fit.tjv.popovle1.semestral.converter;

import cz.cvut.fit.tjv.popovle1.semestral.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudioConverter {
//    public static Studio fromDTO(StudioDTO studioDTO) {
//        return new Studio(322L, studioDTO.getName(), studioDTO.getCountry());
//    }

    public static StudioDTO toDTO(Studio studio) {
        List<Long> devsIds = new ArrayList<>();
        if (studio.getDevs() != null)
            for (Dev dev : studio.getDevs())
                devsIds.add(dev.getId());
        return new StudioDTO(studio.getName(), studio.getCountry(), devsIds);
    }

    public static Collection<StudioDTO> toDTOs(Collection<Studio> studios) {
        return studios.stream().map(StudioConverter::toDTO).toList();
    }
}