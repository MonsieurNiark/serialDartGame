package fr.namelessfox.serialDartGame.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "game_type")
@Getter
@Setter
public class GameType implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_game_type")
	private Integer id;

	@Column(name = "label")
	private String label;
	
	@Column(name = "max_score")
	private int maxScore;
}
