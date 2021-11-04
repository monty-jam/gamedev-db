package cz.cvut.fit.tjv.popovle1.semestral.dto;

public class StudioDTO {
    private final String Name;
    private final String Country;

    public StudioDTO(String name, String country) {
        Name = name;
        Country = country;
    }

    public String getName() {
        return Name;
    }

    public String getCountry() {
        return Country;
    }
}
