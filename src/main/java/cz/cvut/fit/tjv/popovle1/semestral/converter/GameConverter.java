package cz.cvut.fit.tjv.popovle1.semestral.converter;

import cz.cvut.fit.tjv.popovle1.semestral.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameConverter {
//    public static Game fromDTO(GameDTO gameDTO) {
//        return new Game(322L, gameDTO.getName(), gameDTO.getGenre());
//    }

    public static GameDTO toDTO(Game game) {
        List<Long> studiosIds = new ArrayList<>();
        if (game.getStudios() != null)
            for (Studio studio : game.getStudios())
                studiosIds.add(studio.getId());

        return new GameDTO(game.getName(), game.getGenre(), studiosIds);
    }

    public static Collection<GameDTO> toDTOs(Collection<Game> games) {
        return games.stream().map(GameConverter::toDTO).toList();
    }
}