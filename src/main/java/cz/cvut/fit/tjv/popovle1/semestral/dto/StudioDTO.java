package cz.cvut.fit.tjv.popovle1.semestral.dto;

import java.util.List;

public class StudioDTO {
    private final String name;
    private final String country;
    private List<Long> devsIds = null;
    private List<Long> gamesIds = null;

    public StudioDTO(String name, String country, List<Long> devsIds, List<Long> gamesIds) {
        this.name = name;
        this.country = country;
        this.devsIds = devsIds;
        this.gamesIds = gamesIds;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public List<Long> getDevsIds() {
        return devsIds;
    }

    public List<Long> getGamesIds() {
        return gamesIds;
    }
}
