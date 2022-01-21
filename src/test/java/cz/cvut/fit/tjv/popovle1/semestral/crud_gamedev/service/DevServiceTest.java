package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.StudioRepo;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class DevServiceTest {

    @Autowired
    private DevService devService;
    @MockBean
    private DevRepo devRepo;
    @MockBean
    private StudioRepo studioRepo;

    private final Dev dev = new Dev("John", "Carmack", "Programmer", null);
    private final DevDTO devDTO = new DevDTO(null, "John", "Carmack", "Programmer", null);
    private final DevDTO newDevDTO = new DevDTO(null, "Edmund", "McMillen", "Art Designer", null);

    @Test
    void createTest() {
    }



}
