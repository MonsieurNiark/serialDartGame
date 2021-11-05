package fr.namelessfox.serialDartGame.vaadin.view;

import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import fr.namelessfox.serialDartGame.dto.DartCaseDto;
import fr.namelessfox.serialDartGame.dto.DartInputDto;
import fr.namelessfox.serialDartGame.dto.GameDto;
import fr.namelessfox.serialDartGame.dto.PlayerDto;
import fr.namelessfox.serialDartGame.serial.SerialInput;
import fr.namelessfox.serialDartGame.service.DartCaseService;
import fr.namelessfox.serialDartGame.service.DartInputService;
import fr.namelessfox.serialDartGame.service.GameService;
import fr.namelessfox.serialDartGame.vaadin.MainLayout;
import fr.namelessfox.serialDartGame.vaadin.config.ConstantesRoutes;
import net.bytebuddy.dynamic.Nexus;

@Route(value = ConstantesRoutes.PLAYING, layout = MainLayout.class)
public class PlayingView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

	private static Logger logger = LogManager.getLogger(PlayingView.class);

	private GameDto gameInput;
	private GameDto gameDto;
	private final GameService gameService;
	private final DartInputService dartInputService;
	private List<PlayerDto> joueurs;

	private final DartCaseService dartCaseService;

	public static int i = 0;

	private NormalGameThread thread;

	private SerialInput serialInput;

	private Label caseToucheNumber;
	private Label scoreNumber;
	private Label joueurActuelLabel;

	private Grid<PlayerDto> playersGrid;

	private Button joueurSuivantButton;
	private Button debugButton;

	private Image flechette1 = new Image("img/flechette.png", "flechette1");
	private Image flechette2 = new Image("img/flechette.png", "flechette2");
	private Image flechette3 = new Image("img/flechette.png", "flechette3");

	private Image flechetteOff1 = new Image("img/flechette-off.png", "flechette1");
	private Image flechetteOff2 = new Image("img/flechette-off.png", "flechette2");
	private Image flechetteOff3 = new Image("img/flechette-off.png", "flechette3");

	private HorizontalLayout joueurHL;
	private HorizontalLayout scoreHL;
	private HorizontalLayout victoireHL;
	private HorizontalLayout serialPortHL;
	private HorizontalLayout caseHL;
	private Label victoireUsername;

	@Autowired
	public PlayingView(GameService gameService, DartCaseService dartCaseService, DartInputService dartInputService) {
		this.gameService = gameService;
		this.dartCaseService = dartCaseService;
		this.dartInputService = dartInputService;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		Location location = event.getLocation();
		QueryParameters queryParameters = location.getQueryParameters();
		List<String> parametersMap = queryParameters.getParameters().get("game");
		if (parametersMap == null) {
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
		if (this.gameDto == null) {
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

		// Info partie
		System.out.println("label id");
		Label gameName = new Label("ID " + this.gameDto.getId() + " Nom du jeu : " + this.gameDto.getGameName()
				+ " Type : " + this.gameDto.getGameType().getLabel());

		// debug button
		debugButton = new Button("debug");

		// Info joueur actuel
		joueurActuelLabel = new Label("xxx");
		joueurActuelLabel.setClassName("info-principale");
		joueurHL = new HorizontalLayout();
		
		// Joueur suivant
		joueurSuivantButton = new Button("Joueur suivant");
		joueurSuivantButton.setIcon(VaadinIcon.ARROW_RIGHT.create());
		joueurSuivantButton.getStyle().set("color", "green");
		joueurSuivantButton.setHeightFull();
		joueurSuivantButton.setWidthFull();
		flechette1.setHeight("150px");
		flechette1.setWidth("100px");
		flechette2.setHeight("150px");
		flechette2.setWidth("100px");
		flechette3.setHeight("150px");
		flechette3.setWidth("100px");

		flechetteOff1.setHeight("150px");
		flechetteOff1.setWidth("100px");
		flechetteOff1.setVisible(false);
		flechetteOff2.setHeight("150px");
		flechetteOff2.setWidth("100px");
		flechetteOff2.setVisible(false);
		flechetteOff3.setHeight("150px");
		flechetteOff3.setWidth("100px");
		flechetteOff3.setVisible(false);
		joueurHL.add(joueurActuelLabel, flechette1, flechette2, flechette3, flechetteOff1, flechetteOff2, flechetteOff3,
				joueurSuivantButton);
		joueurHL.setAlignItems(Alignment.CENTER);

		// Info score
		Label score = new Label("Score restant : ");
		score.setClassName("info-principale");
		scoreNumber = new Label("xxx");
		scoreNumber.setClassName("info-principale");
		scoreHL = new HorizontalLayout();
		scoreHL.add(score, scoreNumber);

		// Victoire de
		Label victoire = new Label("Victoire de ");
		victoire.setClassName("info-principale");
		victoireUsername = new Label();
		victoireUsername.setClassName("info-principale");
		victoireHL = new HorizontalLayout();
		victoireHL.add(victoire, victoireUsername);
		victoireHL.setVisible(false);

		// Case touché
		caseHL = new HorizontalLayout();
		Label caseTouche = new Label("Points : ");
		caseTouche.setClassName("info-principale");
		caseToucheNumber = new Label("xxx");
		caseToucheNumber.setClassName("info-principale");
		caseHL.add(caseTouche, caseToucheNumber);

		// Gestion port serial
		serialPortHL = new HorizontalLayout();
		Select<String> selectPortCom = new Select<>();
		selectPortCom.setItems(serialInput.getPortNames());
		Button startSerialPort = new Button("Start COM", new Icon(VaadinIcon.START_COG));
		Button stopSerialPort = new Button("Stop COM", new Icon(VaadinIcon.START_COG));
		serialPortHL.add(selectPortCom, startSerialPort, stopSerialPort);
		startSerialPort.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (selectPortCom.getValue() != null) {
					serialInput.setPort(selectPortCom.getValue());
					serialInput.startSerialReading();
					if (!thread.isAlive()) {
						thread.start();
					}

				}
			}
		});
		stopSerialPort.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (serialInput.isOpen()) {
					serialInput.close();
				}
			}
		});

		playersGrid = new Grid<>(PlayerDto.class);
		playersGrid.removeColumnByKey("id");
		playersGrid.removeColumnByKey("nombreDeLancee");
		playersGrid.setColumns("username", "nombreDeLanceeTotal", "score", "scoreRestant");
		playersGrid.setItems(joueurs);
		mainLayout.add(gameName, victoireHL, caseHL, scoreHL, joueurHL, playersGrid, serialPortHL);
		mainLayout.setAlignItems(Alignment.CENTER);
		add(mainLayout);
		setClassName("default-background");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		loadCurrentGameIfExist();
		logger.info("end view loading");

	}

	private void createGame() {
		logger.info("create game");
		this.gameDto = gameService.save(gameInput);
	}

	private void loadCurrentGameIfExist() {
		logger.info("loadCurrentGameIfExist");
		PlayerDto playerDtoVictoire = null;
		for (PlayerDto player : gameDto.getPlayers()) {
			int nbLanceeTotal = 0;
			List<DartInputDto> darts = dartInputService.findInputsForPlayerForGame(gameDto.getId(), player.getId());
			for (DartInputDto dartInputDto : darts) {
				nbLanceeTotal++;
				player.setScore(player.getScore() + dartInputDto.getScore());
			}
			player.setNombreDeLanceeTotal(nbLanceeTotal);
			player.setScoreRestant(gameDto.getGameType().getMaxScore() - player.getScore());
			if (player.getScoreRestant() == 0) {
				playerDtoVictoire = player;
			}
		}
		if (playerDtoVictoire != null) {
			loadVictoryGameExist(playerDtoVictoire);
		}
	}

	private void loadVictoryGameExist(PlayerDto playerDto) {
		joueurHL.setVisible(false);
		scoreHL.setVisible(false);

		victoireHL.setVisible(true);
		serialPortHL.setVisible(false);

		caseHL.setVisible(false);
		victoireUsername.setText(playerDto.getUsername());
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		logger.info("on attach");
		thread = new NormalGameThread(attachEvent.getUI(), this, serialInput, joueurs, dartInputService, gameService);
		thread.setGameDto(this.gameDto);

	}

	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		logger.info("before leave");
		try {
			if (serialInput.isOpen()) {
				serialInput.close();
			}
		} catch (Exception e) {

		}

	}

	private static class NormalGameThread extends Thread {
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

		private DartInputService dartInputService;
		private GameService gameService;

		private GameDto gameDto;

		private boolean isActive = true;

		public NormalGameThread(UI ui, PlayingView view, SerialInput serialInput, List<PlayerDto> players,
				DartInputService dartInputService, GameService gameService) {
			logger.info("NormalGameThread constructor");
			this.ui = ui;
			this.view = view;
			this.serialInput = serialInput;
			this.actualPlayer = players.get(indexPlayer);
			this.players = players;
			this.dartInputService = dartInputService;
			this.gameService = gameService;
		}

		public void setGameDto(GameDto gameDto) {
			this.gameDto = gameDto;
		}

		@Override
		public void run() {
			logger.info("start NormalGameThread");
			ui.access(() -> view.joueurActuelLabel.setText(actualPlayer.getUsername()));
			// debug button
			ui.access(() -> view.debugButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

				@Override
				public void onComponentEvent(ClickEvent<Button> event) {
					victoire();
				}
			}));

			ui.access(() -> view.joueurSuivantButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

				@Override
				public void onComponentEvent(ClickEvent<Button> event) {
					if (confirmNextPlayer == 0) {
						ui.access(() -> view.joueurSuivantButton.getStyle().set("color", "red"));
						confirmNextPlayer++;
					} else {
						confirmNextPlayer = 0;
						changementJoueur();
						ui.access(() -> view.joueurSuivantButton.getStyle().set("color", "green"));
						ui.access(() -> view.scoreNumber.setText(String.valueOf(actualPlayer.getScoreRestant())));

					}
				}
			}));
			consumerSuccess = new Consumer<DartCaseDto>() {

				@Override
				public void accept(DartCaseDto t) {
					ui.access(() -> view.caseToucheNumber.setText(String.valueOf(t.getValue())));
					if (isActive) {
						if (actualPlayer.getNombreDeLancee() <= 2
								|| gameDto.getGameType().getLabel().equals("TRAINING")) {
							if (actualPlayer.getNombreDeLancee() == 0) { // On sauvegarde a la premiere flechette
								previousScore = actualPlayer.getScore();
								previousScoreLeft = actualPlayer.getScoreRestant();
							}
							actualPlayer.setNombreDeLancee(actualPlayer.getNombreDeLancee() + 1);
							actualPlayer.setNombreDeLanceeTotal(actualPlayer.getNombreDeLanceeTotal() + 1);
							if (actualPlayer.getNombreDeLancee() == 1) {
								ui.access(() -> view.flechette3.setVisible(false));
								ui.access(() -> view.flechetteOff3.setVisible(true));
							} else if (actualPlayer.getNombreDeLancee() == 2) {
								ui.access(() -> view.flechette2.setVisible(false));
								ui.access(() -> view.flechetteOff2.setVisible(true));
							} else if (actualPlayer.getNombreDeLancee() == 3) {
								ui.access(() -> view.flechette1.setVisible(false));
								ui.access(() -> view.flechetteOff1.setVisible(true));
							}

							if ((actualPlayer.getScoreRestant() - t.getValue()) == 0) { // Check victoire
								actualPlayer.setScore(actualPlayer.getScore() + t.getValue());
								actualPlayer.setScoreRestant(actualPlayer.getScoreRestant() - t.getValue());
								saveDartInput(t);
								ui.access(
										() -> view.scoreNumber.setText(String.valueOf(actualPlayer.getScoreRestant())));
								victoire();
							} else if (actualPlayer.getScoreRestant() - t.getValue() < 0) { // Score precedent +
																							// changement joueur
								scoreFailed();
							} else {
								saveDartInput(t);
								actualPlayer.setScore(actualPlayer.getScore() + t.getValue());
								actualPlayer.setScoreRestant(actualPlayer.getScoreRestant() - t.getValue());
								ui.access(
										() -> view.scoreNumber.setText(String.valueOf(actualPlayer.getScoreRestant())));
							}
						}
					} else {
						players.stream().forEach(new Consumer<PlayerDto>() {

							@Override
							public void accept(PlayerDto t) {
								// TODO Auto-generated method stub
								if (t.getScoreRestant() == 0) {
									actualPlayer = t;
									victoire();
								}
							}
						});
					}

					ui.access(() -> view.playersGrid.getDataProvider().refreshAll());
				}
			};
			serialInput.readData(consumerSuccess);
		}

		private void changementJoueur() {
			actualPlayer.setNombreDeLancee(0);
			indexPlayer++;
			if (indexPlayer > (players.size() - 1)) {
				indexPlayer = 0;
			}
			actualPlayer = players.get(indexPlayer);
			ui.access(() -> view.joueurActuelLabel.setText(actualPlayer.getUsername()));

			ui.access(() -> view.flechette1.setVisible(true));
			ui.access(() -> view.flechette2.setVisible(true));
			ui.access(() -> view.flechette3.setVisible(true));

			ui.access(() -> view.flechetteOff1.setVisible(false));
			ui.access(() -> view.flechetteOff2.setVisible(false));
			ui.access(() -> view.flechetteOff3.setVisible(false));
			System.out.println("Au tour de " + actualPlayer.getUsername());
		}

		private void victoire() {
			System.out.println("victoire de " + actualPlayer.getUsername());
			gameService.setGameActive(false, gameDto.getId());
			gameService.setIdPlayerWin(actualPlayer.getId(), gameDto.getId());
			isActive = false;
			ui.access(() -> view.joueurHL.setVisible(false));
			ui.access(() -> view.scoreHL.setVisible(false));

			ui.access(() -> view.victoireHL.setVisible(true));
			ui.access(() -> view.victoireUsername.setText(actualPlayer.getUsername()));

		}

		private void scoreFailed() {
			System.out.println("BUZZ de " + actualPlayer.getUsername());
			actualPlayer.setScore(previousScore);
			actualPlayer.setScoreRestant(previousScoreLeft);
			actualPlayer.setNombreDeLancee(3);
		}

		private void saveDartInput(DartCaseDto dartCaseDto) {
			DartInputDto dartInputDto = DartInputDto.builder().game(this.gameDto).player(actualPlayer)
					.score(dartCaseDto.getValue()).input(dartCaseDto.getLabel())
					.acurate(1)
					.tentative(1)
					.build();
			dartInputService.save(dartInputDto);
		}

	}

}
