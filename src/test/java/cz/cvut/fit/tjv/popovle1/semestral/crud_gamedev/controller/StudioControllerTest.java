package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.controller;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.AlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.DevService;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.StudioService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(StudioController.class)
@AutoConfigureMockMvc
public class StudioControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StudioService studioService;

    @Test
    void createTest() throws Exception {
        StudioDTO createStudioDTO = new StudioDTO(null, "id Software", "United States of America", List.of(1), List.of(1));

        BDDMockito.given(studioService.create(any(StudioDTO.class))).willReturn(createStudioDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/studios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"id Software\", \"country\":\"United States of America\", \"devsIds\": [1], \"gamesIds\": [1]}")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(createStudioDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(createStudioDTO.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.devsIds", CoreMatchers.is(createStudioDTO.getDevsIds())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gamesIds", CoreMatchers.is(createStudioDTO.getGamesIds())));

        BDDMockito.given(studioService.create(any(StudioDTO.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/studios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ubisoft\", \"country\":\"France\", \"devsIds\": [3], \"gamesIds\": [3]}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        BDDMockito.given(studioService.create(any(StudioDTO.class))).willThrow(AlreadyExistsException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/studios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"id Software\", \"country\":\"United States of America\", \"devsIds\": [1], \"gamesIds\": [1]}")
        ).andExpect(MockMvcResultMatchers.status().isConflict());

        Mockito.verify(studioService, Mockito.atLeast(3)).create(any(StudioDTO.class));
    }

    @Test
    void readTest() throws Exception {
        StudioDTO readStudioDTO = new StudioDTO(null, "id Software", "United States of America", List.of(1), List.of(1));

        BDDMockito.given(studioService.read(any(Integer.class))).willReturn(readStudioDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/studios/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"id Software\", \"country\":\"United States of America\", \"devsIds\": [1], \"gamesIds\": [1]}")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(readStudioDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(readStudioDTO.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.devsIds", CoreMatchers.is(readStudioDTO.getDevsIds())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gamesIds", CoreMatchers.is(readStudioDTO.getGamesIds())));

        BDDMockito.given(studioService.read(any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/studios/{id}", 3)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"id Software\", \"country\":\"United States of America\", \"devsIds\": [1], \"gamesIds\": [1]}")
                ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(studioService, Mockito.atLeast(2)).read(any(Integer.class));
    }
    
    @Test
    void readAllTest() throws Exception {
        Collection<StudioDTO> studioDTOs = new ArrayList<>();
        studioDTOs.add(new StudioDTO(null, "id Software", "United States of America", List.of(1), List.of(1)));
        studioDTOs.add(new StudioDTO(null, "Ubisoft", "France", List.of(2), List.of(2)));

        BDDMockito.given(studioService.readAll()).willReturn(studioDTOs);
        List<StudioDTO> studioList = new ArrayList<>(studioDTOs);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/studios")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(studioList.get(0).getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].country", CoreMatchers.is(studioList.get(0).getCountry())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].devsIds", CoreMatchers.is(studioList.get(0).getDevsIds())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].gamesIds", CoreMatchers.is(studioList.get(0).getGamesIds())))

        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", CoreMatchers.is(studioList.get(1).getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].country", CoreMatchers.is(studioList.get(1).getCountry())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].devsIds", CoreMatchers.is(studioList.get(1).getDevsIds())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].gamesIds", CoreMatchers.is(studioList.get(1).getGamesIds())));

        Mockito.verify(studioService, Mockito.atLeastOnce()).readAll();
    }

    @Test
    void updateTest() throws Exception {
        StudioDTO updateStudioDTO = new StudioDTO(null, "Ubisoft", "France", List.of(2), List.of(2));

        BDDMockito.given(studioService.update(any(StudioDTO.class), any(Integer.class))).willReturn(updateStudioDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/studios/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Ubisoft\", \"country\":\"France\", \"devsIds\": [2], \"gamesIds\": [2]}")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(updateStudioDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(updateStudioDTO.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.devsIds", CoreMatchers.is(updateStudioDTO.getDevsIds())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gamesIds", CoreMatchers.is(updateStudioDTO.getGamesIds())));

        BDDMockito.given(studioService.update(any(StudioDTO.class), any(Integer.class))).willThrow(AlreadyExistsException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/studios/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ubisoft\", \"country\":\"France\", \"devsIds\": [2], \"gamesIds\": [2]}")
        ).andExpect(MockMvcResultMatchers.status().isConflict());

        BDDMockito.given(studioService.update(any(StudioDTO.class), any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/studios/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ubisoft\", \"country\":\"France\", \"devsIds\": [2], \"gamesIds\": [2]}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(studioService, Mockito.atLeast(3)).update(any(StudioDTO.class), any(Integer.class));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/studios/{id}", 1)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(studioService, Mockito.atLeast(1)).delete(any(Integer.class));
    }
}
