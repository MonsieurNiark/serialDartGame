package fr.namelessfox.serialDartGame.dto;

import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DartInputDto {

	private final Integer id;
	
	private final GameDto game;
	
	private final PlayerDto player;
	
	private final Integer score;
	
	private final String input;
	
	private final float acurate;
	
	private final Integer tentative;
}
