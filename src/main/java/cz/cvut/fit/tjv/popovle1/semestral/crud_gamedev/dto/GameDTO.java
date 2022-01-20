package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto;

import java.util.List;

public class GameDTO {
    private final Long id;
    private final String name;
    private final String genre;
    private final List<Long> studiosIds;

    public GameDTO(Long id, String name, String genre, List<Long> studiosIds) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.studiosIds = studiosIds;
    }

    public Long getId() { return id; }

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
