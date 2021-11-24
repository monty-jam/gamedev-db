package cz.cvut.fit.tjv.popovle1.semestral.dto;

import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;

public class DevDTO {
    private final String name;
    private final String surname;
    private final String specialization;
    private Long studioId = null;

    public DevDTO(String name, String surname, String specialization, Long studioId) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.studioId = studioId;
    }

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
