package fr.namelessfox.serialDartGame.service;

import fr.namelessfox.serialDartGame.dto.GameDto;

public interface GameService {
	
	GameDto save(GameDto gameDto);

	GameDto findByName(String gameName);
}
