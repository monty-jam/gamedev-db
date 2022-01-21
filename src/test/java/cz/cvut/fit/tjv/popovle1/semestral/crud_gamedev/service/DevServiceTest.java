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

import java.util.Collection;
import java.util.List;
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

    private final Studio studio = new Studio(1, "id Software", "United States of America", null, null);
    private final Studio newStudio = new Studio(2, "Ubisoft", "France", null, null);

    // For tests without studio
    private final Dev dev1 = new Dev("John", "Carmack", "Programmer", null);
    private final DevDTO devDTO1 = new DevDTO(null, "John", "Carmack", "Programmer", null);
    private final Dev newDev1 = new Dev("Michel", "Ancel", "Art Designer", null);
    private final DevDTO newDevDTO1 = new DevDTO(null, "Michel", "Ancel", "Art Designer", null);

    // For tests with studio
    private final Dev dev2 = new Dev("John", "Carmack", "Programmer", studio);
    private final DevDTO devDTO2 = new DevDTO(null, "John", "Carmack", "Programmer", 1);
    private final Dev newDev2 = new Dev("Michel", "Ancel", "Art Designer", newStudio);
    private final DevDTO newDevDTO2 = new DevDTO(null, "Michel", "Ancel", "Art Designer", 2);

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
        BDDMockito.given(studioRepo.findById(studio.getId())).willReturn(Optional.of(studio));
        BDDMockito.given(devRepo.save(any(Dev.class))).willReturn(dev2);

        DevDTO retDev = devService.create(devDTO2);

        Assertions.assertEquals(retDev.getName(), devDTO2.getName());
        Assertions.assertEquals(retDev.getSurname(), devDTO2.getSurname());
        Assertions.assertEquals(retDev.getSpecialization(), devDTO2.getSpecialization());
        Assertions.assertEquals(retDev.getStudioId(), devDTO2.getStudioId());

        Mockito.verify(studioRepo, Mockito.times(1)).findById(studio.getId());
        Mockito.verify(devRepo, Mockito.times(1)).save(any(Dev.class));
    }

    @Test
    void readTest() throws Exception {
        BDDMockito.given(devRepo.findById(dev2.getId())).willReturn(Optional.of(dev2));

        DevDTO retDev = devService.read(dev2.getId());

        Assertions.assertEquals(retDev.getName(), devDTO2.getName());
        Assertions.assertEquals(retDev.getSurname(), devDTO2.getSurname());
        Assertions.assertEquals(retDev.getSpecialization(), devDTO2.getSpecialization());
        Assertions.assertEquals(retDev.getStudioId(), devDTO2.getStudioId());

        Mockito.verify(devRepo, Mockito.times(2)).findById(dev2.getId());
    }

    @Test
    void readAllTest() {
        List<Dev> devs = List.of(dev1, dev2);

        BDDMockito.given(devRepo.findAll()).willReturn(devs);

        Collection<DevDTO> retDevs = devService.readAll();

        Assertions.assertEquals(retDevs.size(), 2);
        Mockito.verify(devRepo, Mockito.times(1)).findAll();
    }

    @Test
    void updateTest() throws Exception {
        BDDMockito.given(devRepo.findById(dev1.getId())).willReturn(Optional.of(dev1));
        BDDMockito.given(devRepo.save(any(Dev.class))).willReturn(newDev1);

        DevDTO retDev = devService.update(newDevDTO1, dev1.getId());

        Assertions.assertEquals(retDev.getName(), newDevDTO1.getName());
        Assertions.assertEquals(retDev.getSurname(), newDevDTO1.getSurname());
        Assertions.assertEquals(retDev.getSpecialization(), newDevDTO1.getSpecialization());
        Assertions.assertEquals(retDev.getStudioId(), newDevDTO1.getStudioId());

        Mockito.verify(devRepo, Mockito.times(2)).findById(dev1.getId());
        Mockito.verify(devRepo, Mockito.times(1)).save(any(Dev.class));
    }

    @Test
    void updateWithStudioTest() throws Exception {
        BDDMockito.given(devRepo.findById(dev2.getId())).willReturn(Optional.of(dev2));
        BDDMockito.given(studioRepo.findById(newStudio.getId())).willReturn(Optional.of(newStudio));
        BDDMockito.given(devRepo.save(any(Dev.class))).willReturn(newDev2);

        DevDTO retDev = devService.update(newDevDTO2, dev2.getId());

        Assertions.assertEquals(retDev.getName(), newDevDTO2.getName());
        Assertions.assertEquals(retDev.getSurname(), newDevDTO2.getSurname());
        Assertions.assertEquals(retDev.getSpecialization(), newDevDTO2.getSpecialization());
        Assertions.assertEquals(retDev.getStudioId(), newDevDTO2.getStudioId());

        Mockito.verify(devRepo, Mockito.times(2)).findById(dev2.getId());
        Mockito.verify(studioRepo, Mockito.times(1)).findById(newStudio.getId());
        Mockito.verify(devRepo, Mockito.times(1)).save(any(Dev.class));
    }

    @Test
    void deleteTest() throws Exception {
        BDDMockito.given(devRepo.findById(dev1.getId())).willReturn(Optional.of(dev1));
        devService.delete(dev1.getId());

        Mockito.verify(devRepo, Mockito.times(1)).findById(dev1.getId());
        Mockito.verify(devRepo, Mockito.times(1)).deleteById(dev1.getId());
    }

}
