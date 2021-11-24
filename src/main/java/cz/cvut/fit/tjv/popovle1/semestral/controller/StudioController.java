package cz.cvut.fit.tjv.popovle1.semestral.controller;

import cz.cvut.fit.tjv.popovle1.semestral.converter.DevConverter;
import cz.cvut.fit.tjv.popovle1.semestral.converter.StudioConverter;
import cz.cvut.fit.tjv.popovle1.semestral.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.exception.GameAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.service.StudioService;
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
    public ResponseEntity create(@RequestBody StudioDTO studioDTO) {
        try {
            StudioDTO created = studioService.create(studioDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studioService.read(id));
        } catch (StudioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public Collection<StudioDTO> readAll() {
        return StudioConverter.toDTOs(studioService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody StudioDTO newStudio) {
        try {
            return ResponseEntity.ok(studioService.update(newStudio, id));
        } catch (StudioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            studioService.delete(id);
            return ResponseEntity.ok("Studio is deleted.");
        } catch (StudioNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
