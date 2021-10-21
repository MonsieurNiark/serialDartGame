package fr.namelessfox.serialDartGame.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.namelessfox.serialDartGame.domaine.DartInput;
import fr.namelessfox.serialDartGame.dto.DartInputDto;
import fr.namelessfox.serialDartGame.mapper.DartInputMapper;
import fr.namelessfox.serialDartGame.repository.DartInputRepository;
import fr.namelessfox.serialDartGame.service.DartInputService;

@Service
public class DartInputServiceImpl implements DartInputService {

	private final DartInputRepository dartInputRepository;
	private final DartInputMapper dartInputMapper;
	
	@Autowired
	public DartInputServiceImpl(final DartInputRepository dartInputRepository, final DartInputMapper dartInputMapper) {
		this.dartInputRepository = dartInputRepository;
		this.dartInputMapper = dartInputMapper;
	}
	
	@Override
	public DartInputDto save(DartInputDto dartInputDto) {
		DartInput dartInput = dartInputMapper.dtoToEntity(dartInputDto);
		
		return dartInputMapper.entityToDto(dartInputRepository.save(dartInput));
	}

}
