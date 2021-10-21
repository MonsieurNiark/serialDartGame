package fr.namelessfox.serialDartGame.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import fr.namelessfox.serialDartGame.domaine.Player;
import fr.namelessfox.serialDartGame.dto.PlayerDto;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PlayerMapper {

	PlayerDto entityToDto(final Player player);
	
	Player dtoToEntity(final PlayerDto playerDto);
	
	List<PlayerDto> entityToDtos(final List<Player> players);
}
