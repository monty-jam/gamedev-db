package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto;

public class DevDTO {
    private final Integer id;
    private final String name;
    private final String surname;
    private final String specialization;
    private final Integer studioId;

    public DevDTO(Integer id, String name, String surname, String specialization, Integer studioId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.studioId = studioId;
    }

    public Integer getId() { return id;}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Integer getStudioId() {
        return studioId;
    }
}
