package fr.namelessfox.serialDartGame.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.namelessfox.serialDartGame.domaine.Game;
import fr.namelessfox.serialDartGame.dto.GameDto;
import fr.namelessfox.serialDartGame.mapper.GameMapper;
import fr.namelessfox.serialDartGame.mapper.GameTypeMapper;
import fr.namelessfox.serialDartGame.repository.GameRepository;
import fr.namelessfox.serialDartGame.service.GameService;

@Service
public class GameServiceImpl implements GameService{

	private final GameRepository gameRepository;
	private final GameMapper gameMapper;
	
	private final GameTypeMapper gameTypeMapper;
	
	@Autowired
	public GameServiceImpl(final GameRepository gameRepository, final GameMapper gameMapper, final GameTypeMapper gameTypeMapper) {
		this.gameRepository = gameRepository;
		this.gameMapper = gameMapper;
		this.gameTypeMapper = gameTypeMapper;
	}
	
	@Override
	public GameDto save(GameDto gameDto) {
		Game game = gameMapper.dtoToEntity(gameDto);
		game.setGameType(gameTypeMapper.dtoToEntity(gameDto.getGameType()));
		
		return gameMapper.entityToDto(gameRepository.save(game));
	}
	
	@Override
	public GameDto findByName(String gameName) {
		
		List<Game> games = gameRepository.findByGameName(gameName);
		if(games.size() > 0) {
			return gameMapper.entityToDto(games.get(0));
		}
		return null;
		
	}

	@Override
	public void setGameActive(boolean active, Integer id) {
		gameRepository.setGameActive(active, id);
	}

	@Override
	public List<GameDto> findAllGameDtos() {
		return gameMapper.entityToDtos(gameRepository.findAll());
	}

}
