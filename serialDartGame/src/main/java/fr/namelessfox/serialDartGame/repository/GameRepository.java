package fr.namelessfox.serialDartGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.namelessfox.serialDartGame.domaine.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	List<Game> findByGameName(String gameName);
	
	@Transactional
	@Modifying
	@Query("UPDATE Game g SET g.active=?1 WHERE  g.id = ?2")
	void setGameActive(boolean active, Integer id);
}
