package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String country;

    @OneToMany(mappedBy = "studio")
    private List<Dev> devs;

    @ManyToMany
    @JoinTable(name = "studio_game",
            joinColumns = @JoinColumn(name = "studio_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

    public Studio() {
    }

    public Studio(String name, String country) {
        this.name = name;
        this.country = country;
    }

//    public Studio(Integer id, String name, String country) {
//        this.id = id;
//        this.name = name;
//        this.country = country;
//    }


    public Studio(Integer id, String name, String country, List<Dev> devs, List<Game> games) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.devs = devs;
        this.games = games;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
