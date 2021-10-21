package fr.namelessfox.serialDartGame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.namelessfox.serialDartGame.dto.GameTypeDto;
import fr.namelessfox.serialDartGame.mapper.GameTypeMapper;
import fr.namelessfox.serialDartGame.repository.GameTypeRepository;
import fr.namelessfox.serialDartGame.service.GameTypeService;

@Service
public class GameTypeServiceImpl implements GameTypeService{

	private final GameTypeRepository gameTypeRepository;
	private final GameTypeMapper gameTypeMapper;
	
	@Autowired
	public GameTypeServiceImpl(final GameTypeRepository gameTypeRepository, final GameTypeMapper gameTypeMapper) {
		this.gameTypeRepository = gameTypeRepository;
		this.gameTypeMapper = gameTypeMapper;
	}
	
	@Override
	public List<GameTypeDto> getListGameTypes() {
		return gameTypeMapper.entityToDtos(gameTypeRepository.findAll());
	}

}
