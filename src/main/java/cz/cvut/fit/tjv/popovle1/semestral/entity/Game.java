package cz.cvut.fit.tjv.popovle1.semestral.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String genre;

    @ManyToMany(mappedBy = "games")
    private List<Studio> studios = null;

    public Game() {
    }

    public Game(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

//    public Game(Long id, String name, String genre) {
//        this.id = id;
//        this.name = name;
//        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }
}
