package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto;

public class DevDTO {
    private final Long id;
    private final String name;
    private final String surname;
    private final String specialization;
    private final Long studioId;

    public DevDTO(Long id, String name, String surname, String specialization, Long studioId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.studioId = studioId;
    }

    public Long getId() { return id;}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Long getStudioId() {
        return studioId;
    }
}
