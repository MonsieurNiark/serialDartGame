package fr.namelessfox.serialDartGame.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dart_input")
@Getter
@Setter
public class DartInput implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dart_input")
	private Integer id;
	
	@ManyToOne
	private Game game;
	
	@ManyToOne
	private Player player;
	
	private Integer score;
	
	private Integer input;
}
