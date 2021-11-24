package cz.cvut.fit.tjv.popovle1.semestral.dto;

public class GameDTO {
    private final String name;
    private final String genre;

    public GameDTO(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }
}
