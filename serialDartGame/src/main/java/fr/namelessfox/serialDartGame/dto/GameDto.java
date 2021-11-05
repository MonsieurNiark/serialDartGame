package fr.namelessfox.serialDartGame.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class GameDto implements Serializable {

	private final Integer id;

	private final String gameName;
	
	private final boolean active;
	
	private final GameTypeDto gameType;
	
	private final List<PlayerDto> players;
	
	private final Integer idPlayerWin;
}
