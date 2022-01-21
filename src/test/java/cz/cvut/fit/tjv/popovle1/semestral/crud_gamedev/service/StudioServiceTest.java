package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
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
public class StudioServiceTest {

    @Autowired
    private StudioService studioService;
    @MockBean
    private StudioRepo studioRepo;
    @MockBean
    private DevRepo devRepo;
    @MockBean
    private GameRepo gameRepo;

    private final Dev dev = new Dev(1, "John", "Carmack", "Programmer", null);
    private final Dev newDev =  new Dev(2, "Michel", "Ancel", "Art Designer", null);

    private final Game game = new Game(1, "Doom", "Shooter");
    private final Game newGame = new Game(2, "Rayman", "Platformer");

    private final Studio studio = new Studio(1, "id Software", "United States of America", List.of(dev), List.of(game));
    private final StudioDTO studioDTO = new StudioDTO(null, "id Software", "United States of America", List.of(1), List.of(1));
    private final Studio newStudio = new Studio(2, "Ubisoft", "France", List.of(newDev), List.of(newGame));
    private final StudioDTO newStudioDTO = new StudioDTO(null, "Ubisoft", "France", List.of(2), List.of(2));

    @Test
    void createTest() throws Exception {
        BDDMockito.given(studioRepo.findByName(any(String.class))).willReturn(Optional.empty());

        BDDMockito.given(devRepo.findByIdIn(studioDTO.getDevsIds())).willReturn(studio.getDevs());
        BDDMockito.given(gameRepo.findByIdIn(studioDTO.getGamesIds())).willReturn(studio.getGames());

        BDDMockito.given(studioRepo.save(any(Studio.class))).willReturn(studio);
        StudioDTO retStudio = studioService.create(studioDTO);

        Assertions.assertEquals(retStudio.getName(), studioDTO.getName());
        Assertions.assertEquals(retStudio.getCountry(), studioDTO.getCountry());
        Assertions.assertEquals(retStudio.getDevsIds(), studioDTO.getDevsIds());
        Assertions.assertEquals(retStudio.getGamesIds(), studioDTO.getGamesIds());

        Mockito.verify(studioRepo, Mockito.times(1)).save(any(Studio.class));
    }

    @Test
    void readTest() throws Exception {
        BDDMockito.given(studioRepo.findById(studio.getId())).willReturn(Optional.of(studio));

        StudioDTO retStudio = studioService.read(studio.getId());

        Assertions.assertEquals(retStudio.getName(), studioDTO.getName());
        Assertions.assertEquals(retStudio.getCountry(), studioDTO.getCountry());
        Assertions.assertEquals(retStudio.getDevsIds(), studioDTO.getDevsIds());
        Assertions.assertEquals(retStudio.getGamesIds(), studioDTO.getGamesIds());

        Mockito.verify(studioRepo, Mockito.times(2)).findById(studio.getId());
    }

    @Test
    void readAllTest() {
        List<Studio> studios = List.of(studio, newStudio);

        BDDMockito.given(studioRepo.findAll()).willReturn(studios);

        Collection<StudioDTO> retStudios = studioService.readAll();

        Assertions.assertEquals(retStudios.size(), 2);
        Mockito.verify(studioRepo, Mockito.times(1)).findAll();
    }

    @Test
    void updateTest() throws Exception {
        BDDMockito.given(studioRepo.findById(studio.getId())).willReturn(Optional.of(studio));
        BDDMockito.given(studioRepo.findByName(newStudioDTO.getName())).willReturn(Optional.empty());

        BDDMockito.given(devRepo.findByIdIn(newStudioDTO.getDevsIds())).willReturn(newStudio.getDevs());
        BDDMockito.given(gameRepo.findByIdIn(newStudioDTO.getGamesIds())).willReturn(newStudio.getGames());

        BDDMockito.given(studioRepo.save(any(Studio.class))).willReturn(newStudio);

        StudioDTO retStudio = studioService.update(newStudioDTO, studio.getId());

        Assertions.assertEquals(retStudio.getName(), newStudioDTO.getName());
        Assertions.assertEquals(retStudio.getCountry(), newStudioDTO.getCountry());
        Assertions.assertEquals(retStudio.getDevsIds(), newStudioDTO.getDevsIds());
        Assertions.assertEquals(retStudio.getGamesIds(), newStudioDTO.getGamesIds());

        Mockito.verify(studioRepo, Mockito.times(1)).save(any(Studio.class));
    }

    @Test
    void deleteTest() throws Exception {
        BDDMockito.given(studioRepo.findById(studio.getId())).willReturn(Optional.of(studio));
        studioService.delete(studio.getId());

        Mockito.verify(studioRepo, Mockito.times(1)).findById(studio.getId());
        Mockito.verify(studioRepo, Mockito.times(1)).deleteById(studio.getId());
    }

}
