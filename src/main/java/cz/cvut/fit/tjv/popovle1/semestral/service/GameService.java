package cz.cvut.fit.tjv.popovle1.semestral.service;

import cz.cvut.fit.tjv.popovle1.semestral.converter.GameConverter;
import cz.cvut.fit.tjv.popovle1.semestral.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.exception.GameAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.GameNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.repository.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;

    public GameDTO create(GameDTO gameDTO) throws Exception {
        if (gameRepo.findByName(gameDTO.getName()).isPresent()) {
            throw new GameAlreadyExistsException("Game with this name already exists.");
        }
        return GameConverter.toDTO(gameRepo.save(new Game(gameDTO.getName(), gameDTO.getGenre())));
    }

    public GameDTO read(Long id) throws Exception {
        if (gameRepo.findById(id).isEmpty()) {
            throw new GameNotFoundException("This game is not found.");
        }
        return GameConverter.toDTO(gameRepo.findById(id).get());
    }

    public GameDTO update(GameDTO gameDTO, Long id) throws Exception {
        if (gameRepo.findById(id).isEmpty()) {
            throw new GameNotFoundException("This game is not found.");
        }
        if (gameRepo.findByName(gameDTO.getName()).isPresent()
            && gameRepo.findByName(gameDTO.getName()).get().getId() != id) {
            throw new GameAlreadyExistsException("Game with this name already exists.");
        }
        Game game = gameRepo.findById(id).get();
        game.setName(gameDTO.getName());
        game.setGenre(gameDTO.getGenre());

        return GameConverter.toDTO(gameRepo.save(game));
    }

    public void delete(Long id) throws Exception {
        if (gameRepo.findById(id).isEmpty()) {
            throw new GameNotFoundException("This game is not found.");
        }
        gameRepo.deleteById(id);
    }


}