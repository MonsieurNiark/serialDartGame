package fr.namelessfox.serialDartGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.namelessfox.serialDartGame.domaine.Player;


public interface PlayerRepository extends JpaRepository<Player, Integer> {

}
