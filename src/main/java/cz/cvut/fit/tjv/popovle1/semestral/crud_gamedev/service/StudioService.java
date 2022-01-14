package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.service;

import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.converter.StudioConverter;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.dto.StudioDTO;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.DevRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.GameRepo;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Dev;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Game;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.entity.Studio;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.AlreadyExistsException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception.NotFoundException;
import cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.repository.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudioService {

    @Autowired
    private StudioRepo studioRepo;
    @Autowired
    private DevRepo devRepo;
    @Autowired
    private GameRepo gameRepo;

    public StudioDTO create(StudioDTO studioDTO) throws Exception {
        if (studioRepo.findByName(studioDTO.getName()).isPresent()) {
            throw new AlreadyExistsException("Studio with this name already exists.");
        }

        Studio studio = new Studio(studioDTO.getName(), studioDTO.getCountry());

        // Getting given devs from DTO, catching an exception.
        List<Dev> devs = null;
        if (studioDTO.getDevsIds() != null) {
            devs = (List<Dev>) devRepo.findAllById(studioDTO.getDevsIds());
            if (devs.size() != studioDTO.getDevsIds().size())
                throw new NotFoundException("Some of given devs are not found.");
        }

        // Getting given games from DTO, catching an exception.
        List<Game> games = null;
        if (studioDTO.getGamesIds() != null) {
            games = (List<Game>) gameRepo.findAllById(studioDTO.getGamesIds());
            if (games.size() != studioDTO.getGamesIds().size())
                throw new NotFoundException("Some of given games are not found.");
        }

        // All given devs got the created studio as their studio field.
        if (devs != null)
            for (Dev dev : devs)
                dev.setStudio(studio);

        // All given games add the created studio in their studios list.
        if (games != null)
            for (Game game : games)
                game.getStudios().add(studio);

        studio.setDevs(devs);
        studio.setGames(games);

        return StudioConverter.toDTO(studioRepo.save(studio));
    }

    public StudioDTO read(Long id) throws Exception {
        if (studioRepo.findById(id).isEmpty()) {
            throw new NotFoundException("This studio is not found.");
        }
        return StudioConverter.toDTO(studioRepo.findById(id).get());
    }

    public Collection<Studio> readAll() {
        return studioRepo.findAll();
    }

    public StudioDTO update(StudioDTO studioDTO, Long id) throws Exception {
        if (studioRepo.findById(id).isEmpty()) {
            throw new NotFoundException("This studio is not found.");
        }
        if (studioRepo.findByName(studioDTO.getName()).isPresent()
            && studioRepo.findByName(studioDTO.getName()).get().getId() != id) {
            throw new AlreadyExistsException("Studio with this name already exists.");
        }

        Studio studio = studioRepo.findById(id).get();

        // Getting given devs from DTO, catching an exception.
        List<Dev> devs = null;
        if (studioDTO.getDevsIds() != null) {
            devs = (List<Dev>) devRepo.findAllById(studioDTO.getDevsIds());
            if (devs.size() != studioDTO.getDevsIds().size())
                throw new NotFoundException("Some of given devs are not found.");
        }

        // Getting given games from DTO, catching an exception.
        List<Game> games = null;
        if (studioDTO.getGamesIds() != null) {
            games = (List<Game>) gameRepo.findAllById(studioDTO.getGamesIds());
            if (games.size() != studioDTO.getGamesIds().size())
                throw new NotFoundException("Some of given games are not found.");
        }

        // Clearing studio field in all previous devs of given studio.
        for (Dev dev : studio.getDevs())
            dev.setStudio(null);

        // All new given devs got the updated studio as their studio field.
        if (devs != null)
            for (Dev dev : devs)
                dev.setStudio(studio);

        // Games that previously were in relation with the given studio delete it from their studios lists.
        for (Game game : studio.getGames())
            game.getStudios().remove(studio);

        // All new given games add the updated studio in their studios lists.
        if (games != null)
            for (Game game : games)
                game.getStudios().add(studio);

        studio.setName(studioDTO.getName());
        studio.setCountry(studioDTO.getCountry());
        studio.setDevs(devs);
        studio.setGames(games);

        return StudioConverter.toDTO(studioRepo.save(studio));
    }

    public void delete(Long id) throws Exception {
        Optional<Studio> studio = studioRepo.findById(id);
        if (studio.isEmpty()) {
            throw new NotFoundException("This studio is not found.");
        }

        // Clearing studio field in all previous devs of given studio.
        for (Dev dev : studio.get().getDevs())
            dev.setStudio(null);

        // Games that previously were in relation with given studio delete it from their studios lists.
        for (Game game : studio.get().getGames())
            game.getStudios().remove(studio.get());

        studioRepo.deleteById(id);
    }


}