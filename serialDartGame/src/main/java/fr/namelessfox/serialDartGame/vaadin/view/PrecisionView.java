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

@Route(value = ConstantesRoutes.PRECISION, layout = MainLayout.class)
public class PrecisionView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

	private static Logger logger = LogManager.getLogger(PlayingView.class);

	private GameDto gameInput;
	private GameDto gameDto;
	private List<PlayerDto> joueurs;

	private final GameService gameService;
	private final DartInputService dartInputService;
	private final DartCaseService dartCaseService;

	private PrecisionGameThread thread;

	private SerialInput serialInput;

	private HorizontalLayout joueurHL;
	private HorizontalLayout scoreHL;
	private HorizontalLayout victoireHL;
	private HorizontalLayout serialPortHL;
	private HorizontalLayout caseHL;

	private Button joueurSuivantButton;

	private Label caseToucheNumber;
	private Label scoreNumber;
	private Label joueurActuelLabel;

	private Grid<PlayerDto> playersGrid;

	@Autowired
	public PrecisionView(GameService gameService, DartCaseService dartCaseService, DartInputService dartInputService) {
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

	public void start() {
		serialInput = new SerialInput(dartCaseService);
		if (this.gameDto == null) {
			UI.getCurrent().navigate(ConstantesRoutes.CREATE_GAME);
		}
		joueurs = gameDto.getPlayers();

		final VerticalLayout mainLayout = new VerticalLayout();
		// Info partie
		System.out.println("label id");
		Label gameName = new Label("ID " + this.gameDto.getId() + " Nom du jeu : " + this.gameDto.getGameName()
				+ " Type : " + this.gameDto.getGameType().getLabel());

		// Info joueur actuel
		joueurActuelLabel = new Label("xxx");
		joueurActuelLabel.setClassName("info-principale");
		joueurHL = new HorizontalLayout();
		joueurHL.add(joueurActuelLabel, joueurSuivantButton);
		joueurHL.setAlignItems(Alignment.CENTER);

		// Info score
		Label score = new Label("Score restant : ");
		score.setClassName("info-principale");
		scoreNumber = new Label("xxx");
		scoreNumber.setClassName("info-principale");
		scoreHL = new HorizontalLayout();
		scoreHL.add(score, scoreNumber);

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
		
		mainLayout.add(gameName, victoireHL, caseHL, scoreHL, joueurHL, playersGrid, serialPortHL);
		mainLayout.setAlignItems(Alignment.CENTER);
		add(mainLayout);
		setClassName("default-background");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		logger.info("end view loading");

	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		logger.info("on attach");
		thread = new PrecisionGameThread(attachEvent.getUI(), this, serialInput, joueurs, dartInputService,
				gameService);
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

	private void createGame() {
		logger.info("create game");
		this.gameDto = gameService.save(gameInput);
	}

	private static class PrecisionGameThread extends Thread {

		private final UI ui;
		private final PrecisionView view;
		private final SerialInput serialInput;
		private Consumer<DartCaseDto> consumerSuccess;

		private GameDto gameDto;
		private int indexPlayer = 0;

		private DartInputService dartInputService;
		private GameService gameService;

		private PlayerDto actualPlayer;
		private List<PlayerDto> players;

		private boolean isActive = true;

		public PrecisionGameThread(UI ui, PrecisionView view, SerialInput serialInput, List<PlayerDto> players,
				DartInputService dartInputService, GameService gameService) {
			logger.info("PrecisionGameThread constructor");
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

		private void saveDartInput(DartCaseDto dartCaseDto) {
			DartInputDto dartInputDto = DartInputDto.builder().game(this.gameDto).player(actualPlayer)
					.score(dartCaseDto.getValue()).input(dartCaseDto.getLabel()).acurate(0.1f)
					.tentative(3)
					.build();
			dartInputService.save(dartInputDto);
		}

		@Override
		public void run() {
			logger.info("Start PrecisionGameThread");
			consumerSuccess = new Consumer<DartCaseDto>() {

				@Override
				public void accept(DartCaseDto t) {
					ui.access(() -> view.caseToucheNumber.setText(String.valueOf(t.getValue())));
					if (isActive) {

					}
				}
			};
			serialInput.readData(consumerSuccess);
		}

	}

}
