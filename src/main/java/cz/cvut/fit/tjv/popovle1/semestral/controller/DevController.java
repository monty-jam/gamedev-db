package cz.cvut.fit.tjv.popovle1.semestral.controller;

import cz.cvut.fit.tjv.popovle1.semestral.dto.DevDTO;
import cz.cvut.fit.tjv.popovle1.semestral.exception.DevAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.DevNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.service.DevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devs")
public class DevController {

    @Autowired
    private DevService devService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DevDTO devDTO) {
        try {
            devService.create(devDTO);
            return ResponseEntity.ok("User is saved.");
        } catch (DevAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(devService.read(id));
        } catch (DevNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody DevDTO newDev) {
        try {
            return ResponseEntity.ok(devService.update(newDev, id));
        } catch (DevNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            devService.delete(id);
            return ResponseEntity.ok("Developer is deleted.");
        } catch (DevNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

}
