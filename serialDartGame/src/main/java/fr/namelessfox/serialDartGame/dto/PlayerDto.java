package fr.namelessfox.serialDartGame.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Setter
@Getter
@Builder(toBuilder = true)
public class PlayerDto implements Serializable{
	
	private final Integer id;
	
	private final String username;
	
	private  int score = 0;
	
	private  int scoreRestant;
	
	private  int nombreDeLancee = 0;
	
	private  int nombreDeLanceeTotal = 0;
}
