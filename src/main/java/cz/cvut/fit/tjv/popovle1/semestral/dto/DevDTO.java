package cz.cvut.fit.tjv.popovle1.semestral.dto;

import cz.cvut.fit.tjv.popovle1.semestral.entity.Dev;

import java.util.Optional;

public class DevDTO {
    private Long id;
    private String name;
    private String surname;
    private String specialization;

    public static DevDTO toDTO(Dev dev) {
        DevDTO dto = new DevDTO();
        dto.setId(dev.getId());
        dto.setName(dev.getName());
        dto.setSurname(dev.getSurname());
        dto.setSpecialization(dev.getSpecialization());
        return dto;
    }

    public static Optional<DevDTO> toDTO(Optional<Dev> dev) {
        if (dev.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(dev.get()));
    }

    public DevDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
