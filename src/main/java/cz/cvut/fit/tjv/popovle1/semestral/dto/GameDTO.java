package cz.cvut.fit.tjv.popovle1.semestral.dto;

public class GameDTO {
    private final String Name;
    private final String Genre;

    public GameDTO(String name, String genre) {
        Name = name;
        Genre = genre;
    }

    public String getName() {
        return Name;
    }

    public String getGenre() {
        return Genre;
    }
}
