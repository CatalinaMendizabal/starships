import UI.ChooseShipUI;
import UI.GameOverUI;
import UI.IntroGameUI;
import UI.LoadGameUI;
import controller.AsteroidController;
import edu.austral.dissis.starships.collision.CollisionEngine;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.*;
import factory.AsteroidFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import model.components.data.AsteroidData;
import model.Player;
import model.components.data.PlayerData;
import utils.*;
import model.serializer.GameSerializer;
import model.serializer.GameState;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Game extends GameApplication {

    @Override
    @NonNull
    public WindowSettings setupWindow() {
        return WindowSettings.fromTitle("Starships!");
    }

    @Override
    public Parent initRoot(GameContext context) {
        try {
            return new GameManager(this, context).init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class GameManager {

    RootSetter rootSetter;
    GameContext context;

    MainTimer mainTimer;

    public GameManager(RootSetter rootSetter, GameContext gameContext) {
        this.rootSetter = rootSetter;
        this.context = gameContext;
    }

    boolean isIntro = true;

    Parent init() throws IOException {
        return isIntro ? loadIntro(null) : loadGame(null);
    }

    public Parent loadIntro(GameState gameState) throws IOException {
        Pane pane = new Pane();

        pane.setPrefSize(1920, 1080);

        IntroGameUI introGameUI = new IntroGameUI();

        introGameUI.getStart().setOnMouseClicked(event -> {
            try {
                rootSetter.setRoot(chooseShip(0, gameState));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        introGameUI.getLoaded().setOnMouseClicked(event -> {
            GameSerializer.saveGame(gameState);
            try {
                if (gameState == null) {
                    rootSetter.setRoot(init());
                }
                rootSetter.setRoot(loadGame(GameSerializer.loadGame()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        introGameUI.getExit().setOnMouseClicked(event -> {
            System.exit(0);
        });

        introGameUI.generateIntro(pane);
        return pane;
    }

    private Parent chooseShip(int player, GameState gameState) throws IOException {
        Pane pane = new Pane();
        ChooseShipUI chooseShipUI = new ChooseShipUI();
        Button next = chooseShipUI.getNextButton();
        Button back = chooseShipUI.getBackButton();

        next.setOnMouseClicked(event -> {
            try {
                if (player == ConfigurationReader.PLAYERS - 1) rootSetter.setRoot(loadGame(gameState));
                else rootSetter.setRoot(chooseShip(player + 1, gameState));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        back.setOnMouseClicked(event -> {
            try {
                rootSetter.setRoot(loadIntro(gameState));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        chooseShipUI.generateShipUI(pane, player, rootSetter);

        return pane;
    }

    private Parent loadGame(GameState gameState) throws IOException {

        Pane pane = new Pane();
        LoadGameUI loadGameUI = new LoadGameUI();
        loadGameUI.generateGameUI(pane);

        Player[] players;
        AsteroidController asteroidController;
        ConfigurationReader cr = new ConfigurationReader();


        if (gameState == null) {
            players = cr.getPlayers();
            cr.configKeys(pane);
            asteroidController = new AsteroidController();
        } else {
            players = gameState.getPlayers().stream().map(PlayerData::toPlayer).toArray(Player[]::new);
            cr.configSaveGameKeys(players, pane);
            asteroidController = new AsteroidController(gameState.getAsteroids().stream().map(AsteroidData::toAsteroid).collect(Collectors.toList()));
            pane.getChildren().addAll(asteroidController.getViews());
        }

        setTimerConfiguration(loadGameUI.getImageLoader(), pane, players, asteroidController);

        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                isIntro = !isIntro;
                try {
                    mainTimer.stop();
                    mainTimer.setPaused(true);
                    rootSetter.setRoot(loadIntro(new GameState(players, asteroidController.getAsteroids())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getCode() == KeyCode.ESCAPE) System.exit(0);
        });

        mainTimer.start();

        return pane;
    }

    private void setTimerConfiguration(ImageLoader imageLoader, Pane pane, Player[] players, AsteroidController asteroidController) {
        if (mainTimer == null)
            mainTimer = new MainTimer(rootSetter, players, context.getKeyTracker(), imageLoader, pane, asteroidController);
        mainTimer.setPlayers(players);
        mainTimer.setKeyTracker(context.getKeyTracker());
        mainTimer.setImageLoader(imageLoader);
        mainTimer.setPane(pane);
        mainTimer.setAsteroidController(asteroidController);
    }
}

@Setter
class MainTimer extends GameTimer {
    Player[] players;
    AsteroidController asteroidController;
    KeyTracker keyTracker;
    ImageLoader imageLoader;
    Pane pane;
    AsteroidFactory asteroidFactory = new AsteroidFactory();
    CollisionEngine collisionEngine = new CollisionEngine();
    SpawnAsteroids spawnAsteroids = new SpawnAsteroids();
    PlayerManagement playerManagement = new PlayerManagement();
    GameOverUI gameOverUI;
    RootSetter rootSetter;

    boolean paused = false;
    Boolean[] deathPlayer;
    boolean endGame = false;

    public MainTimer(RootSetter rootSetter, Player[] players, KeyTracker keyTracker, ImageLoader imageLoader, Pane pane, AsteroidController asteroidController) {
        this.rootSetter = rootSetter;
        this.players = players;
        this.keyTracker = keyTracker;
        this.imageLoader = imageLoader;
        this.pane = pane;
        this.asteroidController = asteroidController;
        deathPlayer = new Boolean[players.length];
        Arrays.fill(deathPlayer, false);
        gameOverUI = new GameOverUI(players);
    }

    @SneakyThrows
    @Override
    public void nextFrame(double lastFrame) {
        if (paused) {
            lastFrame = 0;
            paused = false;
        } else if (endGame) {
            stop();
            gameOverUI.display(rootSetter, pane);
        }

        pane.requestFocus();
        playerManagement.updateDeathPlayers(players, deathPlayer);
        endGame = playerManagement.checkEndGame(deathPlayer);
        playerManagement.updatePosition(lastFrame, players, pane, keyTracker, collisionEngine, asteroidController);
        playerManagement.updateHealths(players);
        playerManagement.updateDeaths(players, pane, asteroidController);
        spawnAsteroids.spawnAsteroid(asteroidFactory, asteroidController, imageLoader, pane);
    }
}
