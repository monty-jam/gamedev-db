package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.controller;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter.GameConverter;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.AlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<GameDTO> create(@RequestBody GameDTO gameDTO) throws Exception {
        try {
            GameDTO created = gameService.create(gameDTO);
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
    public ResponseEntity<GameDTO> read(@PathVariable Integer id) throws Exception {
        try {
            return ResponseEntity.ok(gameService.read(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping
    public Collection<GameDTO> readAll() {
        return gameService.readAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> update(@PathVariable Integer id, @RequestBody GameDTO newGame) throws Exception {
        try {
            return ResponseEntity.ok(gameService.update(newGame, id));
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
            gameService.delete(id);
            return ResponseEntity.ok("Game is deleted.");
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/hireHunt/{id}")
    public ResponseEntity<StudioDTO> hireHunt(@PathVariable Integer id, @RequestBody List<String> specializations) throws Exception {
        try {
            return ResponseEntity.ok(gameService.hireHunt(id, specializations));
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}
