package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudioConverter {
//    public static Studio fromDTO(StudioDTO studioDTO) {
//        return new Studio(322L, studioDTO.getName(), studioDTO.getCountry());
//    }

    public static StudioDTO toDTO(Studio studio) {
        List<Integer> devsIds = new ArrayList<>();
        if (studio.getDevs() != null)
            for (Dev dev : studio.getDevs())
                devsIds.add(dev.getId());

        List<Integer> gamesIds = new ArrayList<>();
        if (studio.getGames() != null)
            for (Game game : studio.getGames())
                gamesIds.add(game.getId());

        return new StudioDTO(studio.getId(), studio.getName(), studio.getCountry(), devsIds, gamesIds);
    }

    public static Collection<StudioDTO> toDTOs(Collection<Studio> studios) {
        return studios.stream().map(StudioConverter::toDTO).toList();
    }
}