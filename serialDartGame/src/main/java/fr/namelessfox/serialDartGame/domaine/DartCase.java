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
import lombok.Value;

@Entity
@Table(name = "dart_case")
@Getter
@Setter
public class DartCase implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_dart_case")
	private Integer id;
	
	private String label;
	
	private Integer value;
	
	@Column(name ="input_x")
	private Integer inputX;
	
	@Column(name ="input_y")
	private Integer inputY;

	

}
