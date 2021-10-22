package fr.namelessfox.serialDartGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.namelessfox.serialDartGame.domaine.DartInput;


public interface DartInputRepository extends JpaRepository<DartInput, Integer>{

	@Query("SELECT di FROM DartInput di WHERE di.game.id = ?1 AND di.player.id = ?2")
	List<DartInput> findInputsForPlayerForGame(Integer id_game, Integer id_player);
}
