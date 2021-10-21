package fr.namelessfox.serialDartGame.dto;

import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DartInputDto {

	private final Integer id;
	
	private final GameDto gameDto;
	
	private final PlayerDto playerDto;
	
	private final Integer score;
	
	private final Integer input;
}
