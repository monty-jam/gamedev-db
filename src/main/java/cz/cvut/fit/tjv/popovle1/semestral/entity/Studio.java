package cz.cvut.fit.tjv.popovle1.semestral.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String country;

    @OneToMany(targetEntity = Dev.class)
    @JoinColumn(name = "studio_id")
    private List<Dev> devs = null;

    public Studio() {
    }

    public Studio(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Studio(String name, String country, List<Dev> devs) {
        this.name = name;
        this.country = country;
        this.devs = devs;
    }

//    public Studio(Long id, String name, String country) {
//        this.id = id;
//        this.name = name;
//        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Dev> getDevs() {
        return devs;
    }

    public void setDevs(List<Dev> devs) {
        this.devs = devs;
    }
}
