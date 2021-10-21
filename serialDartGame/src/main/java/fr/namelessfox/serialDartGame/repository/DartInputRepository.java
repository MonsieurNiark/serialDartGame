package fr.namelessfox.serialDartGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.namelessfox.serialDartGame.domaine.DartInput;


public interface DartInputRepository extends JpaRepository<DartInput, Integer>{

}
