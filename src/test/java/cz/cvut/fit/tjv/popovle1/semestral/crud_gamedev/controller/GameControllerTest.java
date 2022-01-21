package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.controller;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.AlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.GameService;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.StudioService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(GameController.class)
@AutoConfigureMockMvc
public class GameControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void createTest() throws Exception {
        GameDTO createGameDTO = new GameDTO(1, "Doom", "Shooter", List.of(1));

        BDDMockito.given(gameService.create(any(GameDTO.class))).willReturn(createGameDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Doom\", \"genre\":\"Shooter\", \"studiosIds\": [1]}")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(createGameDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre", CoreMatchers.is(createGameDTO.getGenre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studiosIds", CoreMatchers.is(createGameDTO.getStudiosIds())));

        BDDMockito.given(gameService.create(any(GameDTO.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Doom\", \"genre\":\"Shooter\", \"studiosIds\": [5]}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        BDDMockito.given(gameService.create(any(GameDTO.class))).willThrow(AlreadyExistsException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Doom\", \"genre\":\"Shooter\", \"studiosIds\": [1]}")
        ).andExpect(MockMvcResultMatchers.status().isConflict());

        Mockito.verify(gameService, Mockito.atLeast(3)).create(any(GameDTO.class));
    }

    @Test
    void readTest() throws Exception {
        GameDTO readGameDTO = new GameDTO(1, "Doom", "Shooter", List.of(1));

        BDDMockito.given(gameService.read(any(Integer.class))).willReturn(readGameDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/games/{id}", 1)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(readGameDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre", CoreMatchers.is(readGameDTO.getGenre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studiosIds", CoreMatchers.is(readGameDTO.getStudiosIds())));

        BDDMockito.given(gameService.read(any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/games/{id}", 5)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(gameService, Mockito.atLeast(2)).read(any(Integer.class));
    }

    @Test
    void readAllTest() throws Exception {
        Collection<GameDTO> gameDTOs = new ArrayList<>();
        gameDTOs.add(new GameDTO(1, "Doom", "Shooter", List.of(1)));
        gameDTOs.add(new GameDTO(2, "Rayman", "Platformer", List.of(2)));

        BDDMockito.given(gameService.readAll()).willReturn(gameDTOs);
        List<GameDTO> gameList = new ArrayList<>(gameDTOs);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/games")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(gameList.get(0).getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre", CoreMatchers.is(gameList.get(0).getGenre())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].studiosIds", CoreMatchers.is(gameList.get(0).getStudiosIds())))

        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", CoreMatchers.is(gameList.get(1).getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre", CoreMatchers.is(gameList.get(1).getGenre())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].studiosIds", CoreMatchers.is(gameList.get(1).getStudiosIds())));

        Mockito.verify(gameService, Mockito.atLeastOnce()).readAll();
    }

    @Test
    void updateTest() throws Exception {
        GameDTO updateGameDTO = new GameDTO(2, "Rayman", "Platformer", List.of(2));

        BDDMockito.given(gameService.update(any(GameDTO.class), any(Integer.class))).willReturn(updateGameDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/games/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rayman\", \"genre\":\"Platformer\", \"studiosIds\": [1]}")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(updateGameDTO.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.genre", CoreMatchers.is(updateGameDTO.getGenre())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.studiosIds", CoreMatchers.is(updateGameDTO.getStudiosIds())));

        BDDMockito.given(gameService.update(any(GameDTO.class), any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/games/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rayman\", \"genre\":\"Platformer\", \"studiosIds\": [1]}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        BDDMockito.given(gameService.update(any(GameDTO.class), any(Integer.class))).willThrow(AlreadyExistsException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/games/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rayman\", \"genre\":\"Platformer\", \"studiosIds\": [1]}")
        ).andExpect(MockMvcResultMatchers.status().isConflict());

        Mockito.verify(gameService, Mockito.atLeast(3)).update(any(GameDTO.class), any(Integer.class));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/games/{id}", 1)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(gameService, Mockito.atLeast(1)).delete(any(Integer.class));
    }

    @Test
    void hireHuntTest() throws Exception {
        StudioDTO hireStudioDTO = new StudioDTO(null, "id Software", "United States of America", List.of(1), null);

        BDDMockito.given(gameService.hireHunt(any(Integer.class), Mockito.anyList())).willReturn(hireStudioDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/games/hireHunt/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"Artist\"]")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.devsIds", CoreMatchers.is(hireStudioDTO.getDevsIds())));

        BDDMockito.given(gameService.hireHunt(any(Integer.class), Mockito.anyList())).willThrow(NotFoundException.class);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/games/hireHunt/{id}", 5)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[\"Artist\"]")
                ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(gameService, Mockito.atLeast(2)).hireHunt(any(Integer.class), Mockito.anyList());
    }
}
