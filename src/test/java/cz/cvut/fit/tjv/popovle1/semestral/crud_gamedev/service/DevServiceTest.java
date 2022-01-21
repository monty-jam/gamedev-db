package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.StudioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class DevServiceTest {

    @Autowired
    private DevService devService;
    @MockBean
    private DevRepo devRepo;
    @MockBean
    private StudioRepo studioRepo;

    private final Dev dev1 = new Dev("John", "Carmack", "Programmer", null);
    private final DevDTO devDTO1 = new DevDTO(null, "John", "Carmack", "Programmer", null);
    private final DevDTO newDevDTO1 = new DevDTO(null, "Edmund", "McMillen", "Art Designer", null);

    private final Studio studio2 = new Studio(1, "id Software", "United States of America", null, null);
    private final Dev dev2 = new Dev("John", "Carmack", "Programmer", studio2);
    private final DevDTO devDTO2 = new DevDTO(null, "John", "Carmack", "Programmer", 1);
    private final DevDTO newDevDTO2 = new DevDTO(null, "Edmund", "McMillen", "Art Designer", 2);

    @Test
    void createTest() throws Exception {
        BDDMockito.given(devRepo.save(any(Dev.class))).willReturn(dev1);
        DevDTO retDev = devService.create(devDTO1);

        Assertions.assertEquals(retDev.getName(), devDTO1.getName());
        Assertions.assertEquals(retDev.getSurname(), devDTO1.getSurname());
        Assertions.assertEquals(retDev.getSpecialization(), devDTO1.getSpecialization());
        Assertions.assertEquals(retDev.getStudioId(), devDTO1.getStudioId());

        Mockito.verify(devRepo, Mockito.times(1)).save(any(Dev.class));
    }

    @Test
    void createWithStudioTest() throws Exception {
        BDDMockito.given(studioRepo.findById(1)).willReturn(Optional.of(studio2));
        BDDMockito.given(devRepo.save(any(Dev.class))).willReturn(dev2);

        DevDTO retDev = devService.create(devDTO2);

        Assertions.assertEquals(retDev.getName(), devDTO2.getName());
        Assertions.assertEquals(retDev.getSurname(), devDTO2.getSurname());
        Assertions.assertEquals(retDev.getSpecialization(), devDTO2.getSpecialization());
        Assertions.assertEquals(retDev.getStudioId(), devDTO2.getStudioId());

        Mockito.verify(studioRepo, Mockito.times(1)).findById(1);
        Mockito.verify(devRepo, Mockito.times(1)).save(any(Dev.class));
    }

    @Test
    void readTest() throws Exception {

    }

    @Test
    void readAllTest() throws Exception {

    }

}
