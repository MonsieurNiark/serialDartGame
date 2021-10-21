package fr.namelessfox.serialDartGame.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import fr.namelessfox.serialDartGame.domaine.GameType;
import fr.namelessfox.serialDartGame.dto.GameTypeDto;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface GameTypeMapper {

	GameTypeDto entityToDto(final GameType gameType);
	
	GameType dtoToEntity(final GameTypeDto gameTypeDto);
	
	List<GameTypeDto> entityToDtos(final List<GameType> gameTypes);
}
