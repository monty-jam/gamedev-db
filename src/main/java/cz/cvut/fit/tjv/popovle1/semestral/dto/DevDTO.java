package cz.cvut.fit.tjv.popovle1.semestral.dto;

import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;

import java.util.Optional;

public class DevDTO {
    private String name;
    private String surname;
    private String specialization;

    public static DevDTO toDTO(Dev dev) {
        DevDTO dto = new DevDTO();
        dto.setName(dev.getName());
        dto.setSurname(dev.getSurname());
        dto.setSpecialization(dev.getSpecialization());
        return dto;
    }

    public DevDTO() {
    }

    public DevDTO(String name, String surname, String specialization) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
