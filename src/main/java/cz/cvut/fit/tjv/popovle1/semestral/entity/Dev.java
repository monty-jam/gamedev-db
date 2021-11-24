package cz.cvut.fit.tjv.popovle1.semestral.entity;

import javax.persistence.*;

@Entity
public class Dev {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String specialization;

    @ManyToOne()
    @JoinColumn(name = "studio_id")
    private Studio studio = null;

    public Dev() {
    }

    public Dev(String name, String surname, String specialization, Studio studio) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.studio = studio;
    }

//    public Dev(Long id, String name, String surname, String specialization, Studio studio) {
//        this.id = id;
//        this.name = name;
//        this.surname = surname;
//        this.specialization = specialization;
//    }

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

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
