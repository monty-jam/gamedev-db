package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto;

import java.util.List;

public class GameDTO {
    private final Integer id;
    private final String name;
    private final String genre;
    private final List<Integer> studiosIds;

    public GameDTO(Integer id, String name, String genre, List<Integer> studiosIds) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.studiosIds = studiosIds;
    }

    public Integer getId() { return id; }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public List<Integer> getStudiosIds() {
        return studiosIds;
    }
}
