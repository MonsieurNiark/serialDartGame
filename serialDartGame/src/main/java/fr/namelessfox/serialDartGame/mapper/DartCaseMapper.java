package fr.namelessfox.serialDartGame.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import fr.namelessfox.serialDartGame.domaine.DartCase;
import fr.namelessfox.serialDartGame.dto.DartCaseDto;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DartCaseMapper {

	DartCaseDto entityToDto(final DartCase dartCase);
	
	DartCase dtoToEntity(final DartCaseDto dartCaseDto);
}
