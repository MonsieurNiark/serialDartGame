package fr.namelessfox.serialDartGame.vaadin.view;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import fr.namelessfox.serialDartGame.vaadin.MainLayout;
import fr.namelessfox.serialDartGame.vaadin.config.ConstantesRoutes;

@Route(value = ConstantesRoutes.HOME, layout = MainLayout.class)
public class HomepageView extends VerticalLayout {

	
	public HomepageView() {
		VerticalLayout gameContextLayout = new VerticalLayout();
		VerticalLayout playerContextLayout = new VerticalLayout();
		
		HorizontalLayout mainLayout = new HorizontalLayout();
		
		Button creerGame = new Button("Créer une partie");
		creerGame.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				event.getSource().getUI().ifPresent(ui -> ui.navigate(ConstantesRoutes.CREATE_GAME));
			}
		});
		
		TextField nomGame = new TextField();
		nomGame.setPlaceholder("Nom de la partie");
		Button rejoindreGame = new Button("Rejoindre une partie");
		rejoindreGame.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// TODO Auto-generated method stub
				String nomGameString = nomGame.getValue();
				if(!nomGameString.isEmpty() && nomGameString != null) {
					Map<String, String> paramsMaps = new HashMap<>();
					paramsMaps.put("game", nomGameString);
					QueryParameters params = QueryParameters.simple(paramsMaps);
					event.getSource().getUI().get().navigate(ConstantesRoutes.PLAYING, params);
				}
			}
		});
		gameContextLayout.setClassName("white-radius tuile-homepage");
		gameContextLayout.add(creerGame, nomGame, rejoindreGame);
		gameContextLayout.setWidthFull();
		gameContextLayout.setAlignItems(Alignment.CENTER);
		
		
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
