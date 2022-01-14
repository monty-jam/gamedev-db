package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto;

import java.util.List;

public class GameDTO {
    private final String name;
    private final String genre;
    private final List<Long> studiosIds;

    public GameDTO(String name, String genre, List<Long> studiosIds) {
        this.name = name;
        this.genre = genre;
        this.studiosIds = studiosIds;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public List<Long> getStudiosIds() {
        return studiosIds;
    }
}
