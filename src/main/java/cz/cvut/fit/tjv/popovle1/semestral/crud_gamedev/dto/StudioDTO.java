package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto;

import java.util.List;

public class StudioDTO {
    private final Integer id;
    private final String name;
    private final String country;
    private final List<Integer> devsIds;
    private final List<Integer> gamesIds;

    public StudioDTO(Integer id, String name, String country, List<Integer> devsIds, List<Integer> gamesIds) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.devsIds = devsIds;
        this.gamesIds = gamesIds;
    }

    public Integer getId() { return id; }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public List<Integer> getDevsIds() {
        return devsIds;
    }

    public List<Integer> getGamesIds() {
        return gamesIds;
    }
}
