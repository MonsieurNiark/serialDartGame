package fr.namelessfox.serialDartGame.service;

import java.util.List;

import fr.namelessfox.serialDartGame.dto.DartInputDto;

public interface DartInputService {

	DartInputDto save(DartInputDto dartInputDto);
	
	List<DartInputDto> findInputsForPlayerForGame(Integer id_game, Integer id_player);
}
