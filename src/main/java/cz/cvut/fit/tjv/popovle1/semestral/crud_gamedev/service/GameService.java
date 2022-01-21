package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter.GameConverter;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter.StudioConverter;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.GameDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.GameRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.AlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private StudioRepo studioRepo;
    @Autowired
    private DevRepo devRepo;

    public GameDTO create(GameDTO gameDTO) throws Exception {
        if (gameRepo.findByName(gameDTO.getName()).isPresent()) {
            throw new AlreadyExistsException("Game with this name already exists.");
        }

        Game game = new Game(gameDTO.getName(), gameDTO.getGenre());

        // Getting given studios from DTO, catching an exception.
        List<Studio> studios = null;
        if (gameDTO.getStudiosIds() != null) {
            studios = studioRepo.findByIdIn(gameDTO.getStudiosIds());
            if (studios.size() != gameDTO.getStudiosIds().size())
                throw new NotFoundException("Some of given studios are not found.");
        }

        // All new given studios add the created game in their games lists.
        if (studios != null)
            for (Studio studio : studios)
                studio.getGames().add(game);

        game.setStudios(studios);
        return GameConverter.toDTO(gameRepo.save(game));
    }

    public GameDTO read(Integer id) throws Exception {
        if (gameRepo.findById(id).isEmpty()) {
            throw new NotFoundException("This game is not found.");
        }
        return GameConverter.toDTO(gameRepo.findById(id).get());
    }

    public Collection<Game> readAll() {
        return gameRepo.findAll();
    }

    public GameDTO update(GameDTO gameDTO, Integer id) throws Exception {
        if (gameRepo.findById(id).isEmpty()) {
            throw new NotFoundException("This game is not found.");
        }
        if (gameRepo.findByName(gameDTO.getName()).isPresent()
                && gameRepo.findByName(gameDTO.getName()).get().getId() != id) {
            throw new AlreadyExistsException("Game with this name already exists.");
        }

        Game game = gameRepo.findById(id).get();

        // Getting given studios from DTO, catching an exception.
        List<Studio> studios = null;
        if (gameDTO.getStudiosIds() != null) {
            studios = studioRepo.findByIdIn(gameDTO.getStudiosIds());
            if (studios.size() != gameDTO.getStudiosIds().size())
                throw new NotFoundException("Some of given studios are not found.");
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

    public void delete(Integer id) throws Exception {
        Optional<Game> game = gameRepo.findById(id);
        if (game.isEmpty()) {
            throw new NotFoundException("This game is not found.");
        }

        // Studios that previously were in relation with given game delete it from their games lists.
        for (Studio studio : game.get().getStudios())
            studio.getGames().remove(game.get());

        gameRepo.deleteById(id);
    }

    // The smallest studio, working on a game with a given id, hires all unemployed developers of given specializations
    public StudioDTO hireHunt(Integer id, List<String> specializations) throws Exception {
        if (gameRepo.findById(id).isEmpty()) {
            throw new NotFoundException("This game is not found.");
        }

        Game game = gameRepo.findById(id).get();
        if (game.getStudios().isEmpty()) {
            throw new Exception("This game is not developed by any studios.");
        }

        Studio minStudio = game.getStudios().get(0);
        int minDevs = minStudio.getDevs().size();
        for (Studio studio : game.getStudios())
            if (studio.getDevs().size() < minDevs) {
                minStudio = studio;
                minDevs = studio.getDevs().size();
            }

        minStudio.getDevs().addAll(devRepo.findBySpecializationInAndStudioIdNull(specializations));

        return StudioConverter.toDTO(studioRepo.save(minStudio));
    }


}