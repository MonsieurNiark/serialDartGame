package fr.namelessfox.serialDartGame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.namelessfox.serialDartGame.dto.PlayerDto;
import fr.namelessfox.serialDartGame.mapper.PlayerMapper;
import fr.namelessfox.serialDartGame.repository.PlayerRepository;
import fr.namelessfox.serialDartGame.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService{

	private final PlayerRepository playerRepository;
	private final PlayerMapper playerMapper;
	
	@Autowired
	public PlayerServiceImpl(final PlayerRepository playerRepository, PlayerMapper playerMapper) {
		this.playerRepository = playerRepository;
		this.playerMapper = playerMapper;
	}
	
	@Override
	public List<PlayerDto> getPlayers() {
		return playerMapper.entityToDtos(playerRepository.findAll());
	}

}
