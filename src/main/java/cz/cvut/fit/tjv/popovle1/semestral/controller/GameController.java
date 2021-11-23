package cz.cvut.fit.tjv.popovle1.semestral.controller;

import cz.cvut.fit.tjv.popovle1.semestral.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.exception.GameAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.GameNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity create(@RequestBody GameDTO gameDTO) {
        try {
            GameDTO created = gameService.create(gameDTO);
            return ResponseEntity.ok(created);
        } catch (GameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gameService.read(id));
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody GameDTO newGame) {
        try {
            return ResponseEntity.ok(gameService.update(newGame, id));
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (GameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            gameService.delete(id);
            return ResponseEntity.ok("Game is deleted.");
        } catch (GameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown error.");
        }
    }

}
