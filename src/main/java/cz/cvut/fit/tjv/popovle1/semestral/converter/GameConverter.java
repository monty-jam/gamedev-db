package cz.cvut.fit.tjv.popovle1.semestral.converter;

import cz.cvut.fit.tjv.popovle1.semestral.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Game;

public class GameConverter {
    public static Game fromDTO(GameDTO gameDTO) {
        return new Game(322L, gameDTO.getName(), gameDTO.getGenre());
    }

    public static GameDTO toDTO(Game game) {
        return new GameDTO(game.getName(), game.getGenre());
    }
}