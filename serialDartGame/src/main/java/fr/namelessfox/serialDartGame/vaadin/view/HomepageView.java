package fr.namelessfox.serialDartGame.vaadin.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import fr.namelessfox.serialDartGame.dto.GameDto;
import fr.namelessfox.serialDartGame.service.GameService;
import fr.namelessfox.serialDartGame.vaadin.MainLayout;
import fr.namelessfox.serialDartGame.vaadin.config.ConstantesRoutes;

@Route(value = ConstantesRoutes.HOME, layout = MainLayout.class)
public class HomepageView extends VerticalLayout {

	
	private final GameService gameService;
	
	@Autowired
	public HomepageView(GameService gameService) {
		this.gameService = gameService;
		VerticalLayout gameContextLayout = new VerticalLayout();
		VerticalLayout playerContextLayout = new VerticalLayout();
		
		HorizontalLayout mainLayout = new HorizontalLayout();
		
		//Partie game
		Button creerGame = new Button("Créer une partie");
		creerGame.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				event.getSource().getUI().ifPresent(ui -> ui.navigate(ConstantesRoutes.CREATE_GAME));
			}
		});
		
		Grid<GameDto> gameGrid = new Grid<>(GameDto.class);
		gameGrid.setItems(gameService.findAllGameDtos());
		gameGrid.removeColumnByKey("players");
		gameGrid.removeColumnByKey("gameType");
		gameGrid.removeColumnByKey("id");
		gameGrid.addColumn(gt -> gt.getGameType().getLabel()).setHeader("Type").setId("gameType");
		
		Button rejoindreGame = new Button("Rejoindre une partie");
		rejoindreGame.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				List<GameDto> lists = new ArrayList<>(gameGrid.getSelectedItems());
				String nomGameString = lists.get(0).getGameName();
				if(!nomGameString.isEmpty() && nomGameString != null) {
					Map<String, String> paramsMaps = new HashMap<>();
					paramsMaps.put("game", nomGameString);
					QueryParameters params = QueryParameters.simple(paramsMaps);
					event.getSource().getUI().get().navigate(ConstantesRoutes.PLAYING, params);
				}
				
				
				
				
			}
		});
		
		
		
		gameContextLayout.setClassName("white-radius tuile-homepage");
		gameContextLayout.add(creerGame, rejoindreGame, gameGrid);
		gameContextLayout.setWidthFull();
		gameContextLayout.setAlignItems(Alignment.CENTER);
		
		//Partie gérer joueur
		Button gererJoueur = new Button("Gérer les joueurs");
		gererJoueur.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				event.getSource().getUI().ifPresent(ui -> ui.navigate(ConstantesRoutes.MANAGE_USER));
			}
		});
		playerContextLayout.setClassName("white-radius tuile-homepage");
		playerContextLayout.add(gererJoueur);
		playerContextLayout.setWidthFull();
		playerContextLayout.setAlignItems(Alignment.CENTER);
		
		mainLayout.add(gameContextLayout, playerContextLayout);
		mainLayout.setWidthFull();
		setAlignItems(Alignment.CENTER);
		setClassName("default-background");
		setSizeFull();
		add(mainLayout);
	}
}
