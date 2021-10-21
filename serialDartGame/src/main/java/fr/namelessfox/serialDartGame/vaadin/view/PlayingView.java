package fr.namelessfox.serialDartGame.vaadin.view;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.PollEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.shared.Registration;

import fr.namelessfox.serialDartGame.dto.DartCaseDto;
import fr.namelessfox.serialDartGame.dto.GameDto;
import fr.namelessfox.serialDartGame.dto.PlayerDto;
import fr.namelessfox.serialDartGame.serial.SerialInput;
import fr.namelessfox.serialDartGame.service.DartCaseService;
import fr.namelessfox.serialDartGame.service.GameService;
import fr.namelessfox.serialDartGame.vaadin.MainLayout;
import fr.namelessfox.serialDartGame.vaadin.config.ConstantesRoutes;
import net.bytebuddy.dynamic.Nexus;

@Route(value = ConstantesRoutes.PLAYING, layout = MainLayout.class)
@JsModule("./src/script.js")
public class PlayingView extends VerticalLayout implements BeforeEnterObserver {

	private GameDto gameInput;
	private GameDto gameDto;
	private final GameService gameService;
	private List<PlayerDto> joueurs;
	
	private final DartCaseService dartCaseService;
		
	public static int i = 0;
	
    private FeederThread thread;
	
	private SerialInput serialInput;
	
	private Label caseToucheNumber;
	private Label scoreNumber;
	private Label joueurActuelLabel;
	
	private Grid<PlayerDto> playersGrid;
	
	private Button joueurSuivantButton;
	
	
	@Autowired
	public PlayingView(GameService gameService, DartCaseService dartCaseService) {
		this.gameService = gameService;
		this.dartCaseService = dartCaseService;
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		Location location = event.getLocation();
		QueryParameters queryParameters = location.getQueryParameters();
		List<String> parametersMap = queryParameters.getParameters().get("game");
		if ( parametersMap == null) {
			this.gameInput = UI.getCurrent().getSession().getAttribute(GameDto.class);
			if (this.gameInput == null) {
				UI.getCurrent().navigate(ConstantesRoutes.CREATE_GAME);
			}
			createGame();
		} else {
			String gameNameAlreadyCreated = parametersMap.get(0);
			this.gameDto = gameService.findByName(gameNameAlreadyCreated);
			
		}
		
		start();
		
	}
	
	private void start() {
		
		serialInput = new SerialInput(dartCaseService);
		UI.getCurrent().setPollInterval(1000);
		if(this.gameDto == null) {
			UI.getCurrent().navigate(ConstantesRoutes.CREATE_GAME);
		}
		joueurs = gameDto.getPlayers();		
		joueurs.stream().forEach(new Consumer<PlayerDto>() {

			@Override
			public void accept(PlayerDto t) {
				// TODO Auto-generated method stub
				t.setScoreRestant(gameDto.getGameType().getMaxScore());
			}
		});
		final VerticalLayout mainLayout = new VerticalLayout();
		
		//Info partie
		Label gameName = new Label("ID "+ this.gameDto.getId() + " Nom du jeu : " + this.gameDto.getGameName()
		+ " Type : "+this.gameDto.getGameType().getLabel());
		
		//Info joueur actuel
		Label joueurLabel = new Label("Joueur : ");
		joueurActuelLabel = new Label("xxx");
		final HorizontalLayout joueurHL = new HorizontalLayout();
		//Joueur suivant
		joueurSuivantButton = new Button("Joueur suivant");
		joueurSuivantButton.setIcon(VaadinIcon.ARROW_RIGHT.create());
		joueurSuivantButton.getStyle().set("color", "green");
		joueurHL.add(joueurLabel, joueurActuelLabel, joueurSuivantButton);
		
		
		
		//Info score
		Label score = new Label("Score restant : ");
		scoreNumber = new Label("xxx");
		final HorizontalLayout scoreHL = new HorizontalLayout();
		scoreHL.add(score, scoreNumber);
		
		//Case touché
		final HorizontalLayout caseHL = new HorizontalLayout();
		Label caseTouche = new Label("Case touchée : ");
		caseToucheNumber = new Label("xxx");
		caseHL.add(caseTouche, caseToucheNumber);
		
		//Gestion port serial
		final HorizontalLayout serialPortHL = new HorizontalLayout();
		Select<String> selectPortCom = new Select<>();
		selectPortCom.setItems(serialInput.getPortNames());
		Button startSerialPort = new Button("Start COM", new Icon(VaadinIcon.START_COG));
		serialPortHL.add(selectPortCom, startSerialPort);
		startSerialPort.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if(selectPortCom.getValue() != null) {
					serialInput.setPort(selectPortCom.getValue());
					serialInput.startSerialReading();
					thread.start();
					
				}
			}
		});
		
		playersGrid = new Grid<>(PlayerDto.class);
		playersGrid.removeColumnByKey("id");
		playersGrid.removeColumnByKey("nombreDeLancee");
		playersGrid.setColumns("username", "nombreDeLanceeTotal", "score", "scoreRestant");
		playersGrid.setItems(joueurs);
		mainLayout.add(gameName, serialPortHL,joueurHL, caseHL, scoreHL , playersGrid);
		add(mainLayout);
	}
	
	private void createGame() {
		this.gameDto = gameService.save(gameInput);
	}


	@Override
    protected void onAttach(AttachEvent attachEvent) {
        thread = new FeederThread(attachEvent.getUI(), this, serialInput, joueurs);
        
    }
	
	private static class FeederThread extends Thread {
        private final UI ui;
        private final PlayingView view;
        private final SerialInput serialInput;
        private Consumer<DartCaseDto> consumerSuccess;
        private int count = 0;

        private PlayerDto actualPlayer;
        private List<PlayerDto> players;
        
        private int previousScore = 0;
        private int previousScoreLeft = 0;
        
        private int indexPlayer = 0;
        
        private int confirmNextPlayer = 0;
        
        public FeederThread(UI ui, PlayingView view, SerialInput serialInput, List<PlayerDto> players) {
            this.ui = ui;
            this.view = view;
            this.serialInput = serialInput;
            this.actualPlayer = players.get(indexPlayer);
            this.players = players;
        }

        @Override
        public void run() {
        	ui.access(() -> view.joueurActuelLabel.setText(actualPlayer.getUsername()));
        	ui.access(() -> view.joueurSuivantButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

				@Override
				public void onComponentEvent(ClickEvent<Button> event) {
					if(confirmNextPlayer == 0) {
						ui.access(() -> view.joueurSuivantButton.getStyle().set("color", "red"));
						confirmNextPlayer++;
					} else {
						confirmNextPlayer = 0;
						changementJoueur();
						ui.access(() -> view.joueurSuivantButton.getStyle().set("color", "green"));

					}
				}
			}));
        	consumerSuccess = new Consumer<DartCaseDto>() {

				@Override
				public void accept(DartCaseDto t) {
					ui.access(() -> view.caseToucheNumber.setText(t.getLabel()));
					if(actualPlayer.getNombreDeLancee() <= 2) {
						if(actualPlayer.getNombreDeLancee() == 0) { //On sauvegarde a la premiere flechette
							previousScore = actualPlayer.getScore();
							previousScoreLeft = actualPlayer.getScoreRestant();
						}
						actualPlayer.setNombreDeLancee(actualPlayer.getNombreDeLancee()+1);
						actualPlayer.setNombreDeLanceeTotal(actualPlayer.getNombreDeLanceeTotal()+1);
						if((actualPlayer.getScoreRestant() - t.getValue()) == 0){ //Check victoire
							victoire();
						} else if (actualPlayer.getScoreRestant() - t.getValue() < 0){ //Score precedent + changement joueur
								
							scoreFailed();
						} else {
							actualPlayer.setScore(actualPlayer.getScore() + t.getValue());
							actualPlayer.setScoreRestant(actualPlayer.getScoreRestant() - t.getValue());
							ui.access(() -> view.scoreNumber.setText(String.valueOf(actualPlayer.getScoreRestant())));
						}
					}
					
					
					ui.access(() -> view.playersGrid.getDataProvider().refreshAll());
				}
			};
        	serialInput.readData(consumerSuccess);
        }
        
        private void changementJoueur() {
        	actualPlayer.setNombreDeLancee(0);
        	indexPlayer++;
        	if(indexPlayer > (players.size()-1)) {
        		indexPlayer = 0;
        	}
        	actualPlayer = players.get(indexPlayer);
        	ui.access(() -> view.joueurActuelLabel.setText(actualPlayer.getUsername()));
        	System.out.println("Au tour de "+actualPlayer.getUsername());
        }
        
        private void victoire() {
        	System.out.println("victoire de "+actualPlayer.getUsername());
        }
        
        private void scoreFailed() {
        	System.out.println("BUZZ de "+ actualPlayer.getUsername());
        	actualPlayer.setScore(previousScore);
        	actualPlayer.setScoreRestant(previousScoreLeft);
        	changementJoueur();
        }
    }
	
}
