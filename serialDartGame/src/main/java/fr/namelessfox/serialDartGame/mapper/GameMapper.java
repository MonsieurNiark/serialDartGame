package fr.namelessfox.serialDartGame.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import fr.namelessfox.serialDartGame.domaine.Game;
import fr.namelessfox.serialDartGame.dto.GameDto;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface GameMapper {
	
	GameDto entityToDto(final Game game);
	
	Game dtoToEntity(final GameDto gameDto);

}
