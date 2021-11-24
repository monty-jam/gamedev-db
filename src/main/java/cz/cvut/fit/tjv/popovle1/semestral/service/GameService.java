package cz.cvut.fit.tjv.popovle1.semestral.service;

import cz.cvut.fit.tjv.popovle1.semestral.converter.GameConverter;
import cz.cvut.fit.tjv.popovle1.semestral.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.exception.GameAlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.GameNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.exception.StudioNotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.repository.GameRepo;
import cz.cvut.fit.tjv.popovle1.semestral.repository.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private StudioRepo studioRepo;

    public GameDTO create(GameDTO gameDTO) throws Exception {
        if (gameRepo.findByName(gameDTO.getName()).isPresent()) {
            throw new GameAlreadyExistsException("Game with this name already exists.");
        }

        Game game = new Game(gameDTO.getName(), gameDTO.getGenre());

        // Getting given studios from DTO, catching an exception.
        List<Studio> studios = null;
        if (gameDTO.getStudiosIds() != null) {
            studios = (List<Studio>) studioRepo.findAllById(gameDTO.getStudiosIds());
            if (studios.size() != gameDTO.getStudiosIds().size())
                throw new StudioNotFoundException("Some of given studios are not found.");
        }

        // All new given studios add the created game in their games lists.
        if (studios != null)
            for (Studio studio : studios)
                studio.getGames().add(game);

        game.setStudios(studios);
        return GameConverter.toDTO(gameRepo.save(game));
    }

    public GameDTO read(Long id) throws Exception {
        if (gameRepo.findById(id).isEmpty()) {
            throw new GameNotFoundException("This game is not found.");
        }
        return GameConverter.toDTO(gameRepo.findById(id).get());
    }

    public Collection<Game> readAll() {
        return gameRepo.findAll();
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

        // Getting given studios from DTO, catching an exception.
        List<Studio> studios = null;
        if (gameDTO.getStudiosIds() != null) {
            studios = (List<Studio>) studioRepo.findAllById(gameDTO.getStudiosIds());
            if (studios.size() != gameDTO.getStudiosIds().size())
                throw new StudioNotFoundException("Some of given studios are not found.");
        }

        // Studios that previously were in relation with the given game delete it from their games lists.
        for (Studio studio : game.getStudios())
            studio.getGames().remove(game);

        // All new given studios add the updated game in their games lists.
        if (studios != null)
            for (Studio studio : studios)
                studio.getGames().add(game);

        game.setName(gameDTO.getName());
        game.setGenre(gameDTO.getGenre());
        game.setStudios(studios);

        return GameConverter.toDTO(gameRepo.save(game));
    }

    public void delete(Long id) throws Exception {
        Optional<Game> game = gameRepo.findById(id);
        if (game.isEmpty()) {
            throw new GameNotFoundException("This game is not found.");
        }

        // Studios that previously were in relation with given game delete it from their games lists.
        for (Studio studio : game.get().getStudios())
            studio.getGames().remove(game.get());

        gameRepo.deleteById(id);
    }


}