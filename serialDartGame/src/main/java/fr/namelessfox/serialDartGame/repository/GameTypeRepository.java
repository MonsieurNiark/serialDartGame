package fr.namelessfox.serialDartGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.namelessfox.serialDartGame.domaine.GameType;

public interface GameTypeRepository extends JpaRepository<GameType, Integer>{

}
