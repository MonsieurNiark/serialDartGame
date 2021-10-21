package fr.namelessfox.serialDartGame.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class GameTypeDto implements Serializable {
	
	private final Integer id;
	
	private final String label;
	
	private final int maxScore;
}
