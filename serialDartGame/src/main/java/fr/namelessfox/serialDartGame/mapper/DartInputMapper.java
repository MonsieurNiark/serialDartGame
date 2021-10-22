package fr.namelessfox.serialDartGame.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import fr.namelessfox.serialDartGame.domaine.DartInput;
import fr.namelessfox.serialDartGame.dto.DartInputDto;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DartInputMapper {

	DartInputDto entityToDto(final DartInput dartInput);
	
	DartInput dtoToEntity(final DartInputDto dartInputDto);
	
	List<DartInputDto> entityToDtos(final List<DartInput> dartInputs);
}
