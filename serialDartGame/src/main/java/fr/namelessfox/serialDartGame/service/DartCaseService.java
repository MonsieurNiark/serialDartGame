package fr.namelessfox.serialDartGame.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.namelessfox.serialDartGame.dto.DartCaseDto;

@Service
public interface DartCaseService {

	Optional<DartCaseDto> getDartCaseByCoord(int x, int y);
	
}
