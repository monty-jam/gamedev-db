package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.controller;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.DevService;
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

@WebMvcTest(DevController.class)
@AutoConfigureMockMvc
public class DevControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DevService devService;

    @Test
    void createTest() throws Exception {
        DevDTO createDevDTO = new DevDTO(null, "John", "Carmack", "Programmer", 1);

        BDDMockito.given(devService.create(any(DevDTO.class))).willReturn(createDevDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/devs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"surname\":\"Carmack\", \"specialization\":\"Programmer\", \"studioId\":\"1\"}")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(createDevDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.is(createDevDTO.getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization", CoreMatchers.is(createDevDTO.getSpecialization())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studioId", CoreMatchers.is(createDevDTO.getStudioId())));

        BDDMockito.given(devService.create(any(DevDTO.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/devs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"John\", \"surname\":\"Carmack\", \"specialization\":\"Programmer\", \"studioId\":\"3\"}")
                ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(devService, Mockito.atLeast(2)).create(any(DevDTO.class));
    }

    @Test
    void readTest() throws Exception {
        DevDTO readDevDTO = new DevDTO(null, "John", "Carmack", "Programmer", 1);

        BDDMockito.given(devService.read(any(Integer.class))).willReturn(readDevDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/devs/{id}", 1)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(readDevDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.is(readDevDTO.getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization", CoreMatchers.is(readDevDTO.getSpecialization())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studioId", CoreMatchers.is(readDevDTO.getStudioId())));

        BDDMockito.given(devService.read(any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/devs/{id}", 3)
                ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(devService, Mockito.atLeast(2)).read(any(Integer.class));
    }

    @Test
    void readAllTest() throws Exception {
        Collection<DevDTO> devDTOs = new ArrayList<>();
        devDTOs.add(new DevDTO(null, "John", "Carmack", "Programmer", null));
        devDTOs.add(new DevDTO(null, "Michel", "Ancel", "Art Designer", null));

        BDDMockito.given(devService.readAll()).willReturn(devDTOs);
        List<DevDTO> devList = new ArrayList<>(devDTOs);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/devs")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(devList.get(0).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname", CoreMatchers.is(devList.get(0).getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].specialization", CoreMatchers.is(devList.get(0).getSpecialization())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].studioId", CoreMatchers.is(devList.get(0).getStudioId())))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", CoreMatchers.is(devList.get(1).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname", CoreMatchers.is(devList.get(1).getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].specialization", CoreMatchers.is(devList.get(1).getSpecialization())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].studioId", CoreMatchers.is(devList.get(1).getStudioId())));

        Mockito.verify(devService, Mockito.atLeastOnce()).readAll();
    }

    @Test
    void updateTest() throws Exception {
        DevDTO updateDevDTO = new DevDTO(null, "Michel", "Ancel", "Art Designer", 2);

        BDDMockito.given(devService.update(any(DevDTO.class), any(Integer.class))).willReturn(updateDevDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/devs/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Michel\", \"surname\":\"Ancel\", \"specialization\":\"Art Designer\", \"studioId\":\"2\"}")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(updateDevDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.is(updateDevDTO.getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization", CoreMatchers.is(updateDevDTO.getSpecialization())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studioId", CoreMatchers.is(updateDevDTO.getStudioId())));

        BDDMockito.given(devService.update(any(DevDTO.class), any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/devs/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Michel\", \"surname\":\"Ancel\", \"specialization\":\"Art Designer\", \"studioId\":\"2\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(devService, Mockito.atLeast(2)).update(any(DevDTO.class), any(Integer.class));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/devs/{id}", 1)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(devService, Mockito.atLeast(1)).delete(any(Integer.class));
    }
}
