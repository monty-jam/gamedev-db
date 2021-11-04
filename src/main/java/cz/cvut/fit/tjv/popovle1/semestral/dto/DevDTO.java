package cz.cvut.fit.tjv.popovle1.semestral.dto;

import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;

public class DevDTO {
    private String name;
    private String surname;
    private String specialization;

    public DevDTO(String name, String surname, String specialization) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
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
}
