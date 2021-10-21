package fr.namelessfox.serialDartGame.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DartCaseDto implements Serializable {
	
	private final Integer id;
	
	private final String label;
	
	private final Integer value;
	
	private final Integer inputX;
	
	private final Integer inputY;

}
