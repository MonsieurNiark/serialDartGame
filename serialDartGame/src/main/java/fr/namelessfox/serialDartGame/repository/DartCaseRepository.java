package fr.namelessfox.serialDartGame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.namelessfox.serialDartGame.domaine.DartCase;

public interface DartCaseRepository extends JpaRepository<DartCase, Integer>{

	@Query(value = "SELECT c FROM DartCase c WHERE c.inputX = ?1 AND c.inputY = ?2")
	Optional<DartCase> findDartCaseByXY(int x, int y);
}
