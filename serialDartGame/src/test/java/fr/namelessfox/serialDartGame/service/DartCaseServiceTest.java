package fr.namelessfox.serialDartGame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import fr.namelessfox.serialDartGame.dto.DartCaseDto;

@ActiveProfiles("local")
@SpringBootTest
public class DartCaseServiceTest {

	@Autowired
	private DartCaseService dartCaseService;
	
	@Test
	void shouldSuccessDart() {
		Optional<DartCaseDto> dartCaseDto = dartCaseService.getDartCaseByCoord(2, 6);//S11
		assertNotNull(dartCaseDto.get());
		assertEquals("S11", dartCaseDto.get().getLabel());
	}
}
