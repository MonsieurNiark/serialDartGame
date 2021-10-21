package fr.namelessfox.serialDartGame.vaadin.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import fr.namelessfox.serialDartGame.dto.GameDto;
import fr.namelessfox.serialDartGame.dto.GameTypeDto;
import fr.namelessfox.serialDartGame.dto.PlayerDto;
import fr.namelessfox.serialDartGame.service.GameTypeService;
import fr.namelessfox.serialDartGame.service.PlayerService;
import fr.namelessfox.serialDartGame.vaadin.MainLayout;
import fr.namelessfox.serialDartGame.vaadin.config.ConstantesRoutes;

@Route(value = ConstantesRoutes.CREATE_GAME, layout = MainLayout.class)
public class CreateGameView extends VerticalLayout{

	private final GameTypeService gameTypeService;
	
	private final PlayerService playerService;
	
	@Autowired
	public CreateGameView(final GameTypeService gameTypeService, PlayerService playerService) {
		this.gameTypeService = gameTypeService;
		this.playerService = playerService;
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setClassName("white-radius");
		mainLayout.setSizeFull();
		
		TextField gameName = new TextField("Nom de la partie");
		Select<GameTypeDto> selectGameType = new Select<>();
		selectGameType.setItemLabelGenerator(GameTypeDto::getLabel);
		List<GameTypeDto> gameTypeDtos = this.gameTypeService.getListGameTypes();
		selectGameType.setItems(gameTypeDtos);
		selectGameType.setValue(gameTypeDtos.get(0));
		Grid<PlayerDto> playerGrid = new Grid<>(PlayerDto.class);
		playerGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		playerGrid.setItems(playerService.getPlayers());
		playerGrid.setColumns("username");
		Button creerGame = new Button("Créer la partie");
		creerGame.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				event.getSource().getUI().ifPresent(new Consumer<UI>() {

					@Override
					public void accept(UI t) {
						if(!gameName.getValue().isEmpty() && selectGameType.getValue() != null && playerGrid.getSelectedItems().size() > 0) {
							GameDto gameDto = GameDto.builder()
									.active(true)
									.gameType(selectGameType.getValue())
									.gameName(gameName.getValue())
									.players(new ArrayList<>(playerGrid.getSelectedItems())).build();
							System.out.println(gameDto.toString());
							UI.getCurrent().getSession().setAttribute(GameDto.class, gameDto);
							t.navigate(ConstantesRoutes.PLAYING);
						}
						
					}
					
				});
				
			}
		});
		mainLayout.add(gameName, selectGameType,playerGrid, creerGame);
		add(mainLayout);
		setClassName("default-background");
		setSizeFull();
	}
}
