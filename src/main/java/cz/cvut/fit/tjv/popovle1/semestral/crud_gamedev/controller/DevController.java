package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.controller;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter.DevConverter;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.DevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/devs")
public class DevController {

    @Autowired
    private DevService devService;

    @PostMapping
    public ResponseEntity<DevDTO> create(@RequestBody DevDTO devDTO) throws Exception {
        try {
            DevDTO created = devService.create(devDTO);
            return ResponseEntity.ok(created);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevDTO> read(@PathVariable Integer id) throws Exception {
        try {
            return ResponseEntity.ok(devService.read(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unknown error.");
        }
    }

    @GetMapping
    public Collection<DevDTO> readAll() {
        return devService.readAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevDTO> update(@PathVariable Integer id, @RequestBody DevDTO newDev) throws Exception {
        try {
            return ResponseEntity.ok(devService.update(newDev, id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws Exception {
        try {
            devService.delete(id);
            return ResponseEntity.ok("Developer is deleted.");
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
