package fr.namelessfox.serialDartGame.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.namelessfox.serialDartGame.domaine.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	List<Game> findByGameName(String gameName);
}
