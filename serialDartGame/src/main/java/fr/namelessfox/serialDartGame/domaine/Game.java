package fr.namelessfox.serialDartGame.domaine;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(toBuilder = true)
public class Game implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_game", nullable = false, unique = true)
	private Integer id;
	
	@Column(name ="game_name")
	private String gameName;

	@Column(name = "active")
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "id_game_type")
	private GameType gameType;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "game_players")
	private List<Player> players;
	
	@Column(name = "id_player_win")
	private Integer idPlayerWin;
}
