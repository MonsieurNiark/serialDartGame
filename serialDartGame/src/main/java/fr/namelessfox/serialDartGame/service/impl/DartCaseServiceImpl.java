package fr.namelessfox.serialDartGame.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.namelessfox.serialDartGame.dto.DartCaseDto;
import fr.namelessfox.serialDartGame.mapper.DartCaseMapper;
import fr.namelessfox.serialDartGame.repository.DartCaseRepository;
import fr.namelessfox.serialDartGame.service.DartCaseService;

@Service
public class DartCaseServiceImpl implements DartCaseService{

	private final DartCaseRepository dartCaseRepository;
	private final DartCaseMapper dartCaseMapper;
	
	@Autowired
	public DartCaseServiceImpl(final DartCaseRepository dartCaseRepository, final DartCaseMapper dartCaseMapper) {
		this.dartCaseRepository = dartCaseRepository;
		this.dartCaseMapper = dartCaseMapper;
	}
	
	@Override
	public Optional<DartCaseDto> getDartCaseByCoord(int x, int y) {
		// TODO Auto-generated method stub
		return dartCaseRepository.findDartCaseByXY(x, y).map(dartCaseMapper::entityToDto);
	}

}
