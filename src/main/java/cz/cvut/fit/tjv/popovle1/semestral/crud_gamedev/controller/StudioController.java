package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.controller;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter.StudioConverter;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.AlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.StudioService;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/studios")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @PostMapping
    public ResponseEntity<StudioDTO> create(@RequestBody StudioDTO studioDTO) throws Exception {
        try {
            StudioDTO created = studioService.create(studioDTO);
            return ResponseEntity.ok(created);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudioDTO> read(@PathVariable Integer id) throws Exception {
        try {
            return ResponseEntity.ok(studioService.read(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping
    public Collection<StudioDTO> readAll() {
        return StudioConverter.toDTOs(studioService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudioDTO> update(@PathVariable Integer id, @RequestBody StudioDTO newStudio) throws Exception {
        try {
            return ResponseEntity.ok(studioService.update(newStudio, id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws Exception {
        try {
            studioService.delete(id);
            return ResponseEntity.ok("Studio is deleted.");
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
