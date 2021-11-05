package fr.namelessfox.serialDartGame.service;

import java.util.List;


import fr.namelessfox.serialDartGame.dto.GameDto;

public interface GameService {
	
	GameDto save(GameDto gameDto);

	GameDto findByName(String gameName);
	
	void setGameActive(boolean active, Integer id);
	
	List<GameDto> findAllGameDtos();
	
	void setIdPlayerWin(Integer idPlayerWin, Integer id);
}
