package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.GameRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.StudioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;
    @MockBean
    private StudioRepo studioRepo;
    @MockBean
    private GameRepo gameRepo;

    private final Studio studio = new Studio(1, "id Software", "United States of America");
    private final StudioDTO studioDTO = new StudioDTO(null, "id Software", "United States of America", null, null);
    private final Studio newStudio = new Studio(2, "Ubisoft", "France");
    private final StudioDTO newStudioDTO = new StudioDTO(null, "Ubisoft", "France", null, null);

    private final Game game = new Game(1, "Doom", "Shooter", List.of(studio));
    private final GameDTO gameDTO = new GameDTO(1, "Doom", "Shooter", List.of(1));
    private final Game newGame = new Game(2, "Rayman", "Platformer", List.of(newStudio));
    private final GameDTO newGameDTO = new GameDTO(2, "Rayman", "Platformer", List.of(2));

    @Test
    void createTest() throws Exception {
        BDDMockito.given(gameRepo.findByName(any(String.class))).willReturn(Optional.empty());
        BDDMockito.given(studioRepo.findByIdIn(gameDTO.getStudiosIds())).willReturn(game.getStudios());

        BDDMockito.given(gameRepo.save(any(Game.class))).willReturn(game);
        GameDTO retGame = gameService.create(gameDTO);

        Assertions.assertEquals(retGame.getName(), gameDTO.getName());
        Assertions.assertEquals(retGame.getGenre(), gameDTO.getGenre());
        Assertions.assertEquals(retGame.getStudiosIds(), gameDTO.getStudiosIds());

        Mockito.verify(gameRepo, Mockito.times(1)).save(any(Game.class));
    }

    @Test
    void readTest() throws Exception {
        BDDMockito.given(gameRepo.findById(game.getId())).willReturn(Optional.of(game));

        GameDTO retGame = gameService.read(game.getId());

        Assertions.assertEquals(retGame.getName(), gameDTO.getName());
        Assertions.assertEquals(retGame.getGenre(), gameDTO.getGenre());
        Assertions.assertEquals(retGame.getStudiosIds(), gameDTO.getStudiosIds());

        Mockito.verify(gameRepo, Mockito.times(2)).findById(game.getId());
    }

    @Test
    void readAllTest() throws Exception {
        List<Game> games = List.of(game, newGame);

        BDDMockito.given(gameRepo.findAll()).willReturn(games);

        Collection<GameDTO> retGames = gameService.readAll();

        Assertions.assertEquals(retGames.size(), 2);
        Mockito.verify(gameRepo, Mockito.times(1)).findAll();
    }

    @Test
    void updateTest() throws Exception {
        BDDMockito.given(gameRepo.findById(game.getId())).willReturn(Optional.of(game));
        BDDMockito.given(gameRepo.findByName(newGameDTO.getName())).willReturn(Optional.empty());

        BDDMockito.given(studioRepo.findByIdIn(newGameDTO.getStudiosIds())).willReturn(newGame.getStudios());

        BDDMockito.given(gameRepo.save(any(Game.class))).willReturn(newGame);

        GameDTO retGame = gameService.update(newGameDTO, game.getId());

        Assertions.assertEquals(retGame.getName(), newGameDTO.getName());
        Assertions.assertEquals(retGame.getGenre(), newGameDTO.getGenre());
        Assertions.assertEquals(retGame.getStudiosIds(), newGameDTO.getStudiosIds());

        Mockito.verify(gameRepo, Mockito.times(1)).save(any(Game.class));
    }

    @Test
    void deleteTest() throws Exception {
        BDDMockito.given(gameRepo.findById(game.getId())).willReturn(Optional.of(game));
        gameService.delete(game.getId());

        Mockito.verify(gameRepo, Mockito.times(1)).findById(game.getId());
        Mockito.verify(gameRepo, Mockito.times(1)).deleteById(game.getId());
    }
}
