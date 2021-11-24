import controller.AsteroidController;
import controller.GameController;
import data.SaveAsteroid;
import data.SavePlayer;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import model.Player;
import serializer.GameSerializer;
import serializer.GameState;
import UI.*;
import utils.ConfigurationReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Game extends GameApplication {

    @Override
    public @NotNull WindowSettings setupWindow() {
        return WindowSettings.fromTitle("Starships!").withSize(1920, 1080);
    }

    @Override
    public Parent initRoot(GameContext context) {

        try {
            ConfigurationReader.reloadConfig();
            return new GameManager(this, context).init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class GameManager {

    final RootSetter rootSetter;
    final GameContext context;
    MainTimer mainTimer;

    public GameManager(RootSetter rootSetter, GameContext gameContext) {
        this.rootSetter = rootSetter;
        this.context = gameContext;
    }

    boolean isIntro = true;

    Parent init() throws IOException {return isIntro ? loadIntro(null) : loadGame(null);}

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

    private Parent loadGame(GameState gameState) throws IOException {

        Pane pane = new Pane();
        LoadGameUI loadGameUI = new LoadGameUI();
        loadGameUI.generateGameUI(pane);

        Player[] players;
        AsteroidController asteroidController;

        if (gameState == null) {
            players = new Player[ConfigurationReader.PLAYERS];
            for (int i = 0; i < ConfigurationReader.PLAYERS; i++) {
                players[i] = new Player(i, 0, ConfigurationReader.LIVES, Objects.requireNonNull(ConfigurationReader.getPlayerShips())[i],
                        ConfigurationReader.PLAYER_KEYS[i][0],
                        ConfigurationReader.PLAYER_KEYS[i][1],
                        ConfigurationReader.PLAYER_KEYS[i][2],
                        ConfigurationReader.PLAYER_KEYS[i][3],
                        ConfigurationReader.PLAYER_KEYS[i][4],
                        ConfigurationReader.PLAYER_KEYS[i][5], true);
            }
            asteroidController = new AsteroidController();
        } else {
            players = gameState.getPlayers().stream().map(SavePlayer::toPlayer).toArray(Player[]::new);
            asteroidController = new AsteroidController(gameState.getAsteroids().stream().map(SaveAsteroid::toAsteroid).collect(Collectors.toList()));
        }

        for (int i = 0, playersLength = players.length; i < playersLength; i++) {
            Player player = players[i];
            player.updateShipStyle(ConfigurationReader.SHIP_NAMES[player.getId()]);

            Group healthView = player.getShipController().getShipView().getHealthView();
            healthView.setLayoutX(10 + 700 * i);
            healthView.setLayoutY(15);
            pane.getChildren().add(healthView);

            Text points = player.getShipController().getShipView().getPoints();
            points.setLayoutX(630 + 700 * i);
            points.setLayoutY(45);
            pane.getChildren().add(points);
        }

        if (mainTimer == null)
            mainTimer = new MainTimer(players, context.getKeyTracker(), pane, asteroidController, rootSetter);
        mainTimer.loadController(players, context.getKeyTracker(), loadGameUI.getImageLoader(), pane, asteroidController);

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
            }

        });
        mainTimer.start();
        return pane;
    }

    private Parent chooseShip(int player, GameState gameState) throws IOException {
        Pane pane = new Pane();
        ChooseShipUI chooseShipUI = new ChooseShipUI();
        Button next = chooseShipUI.getNextButton();
        Button back = chooseShipUI.getBackButton();

        next.setOnMouseClicked(event -> {
            try {
                if (player == ConfigurationReader.PLAYERS - 1) rootSetter.setRoot(loadGame(null));
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

}

@Setter
class MainTimer extends GameTimer {

    GameController gameController;
    GameOverUI gameOverUI;
    RootSetter rootSetter;
    Pane pane;
    Player[] players;
    Boolean[] deathPlayer;

    boolean paused = false;

    public MainTimer(Player[] players, KeyTracker keyTracker, Pane pane, AsteroidController asteroidController, RootSetter rootSetter) {
        deathPlayer = new Boolean[players.length];
        Arrays.fill(deathPlayer, false);
        gameController = new GameController(players, keyTracker, pane, asteroidController, deathPlayer);
        this.pane = pane;
        this.players = players;
        this.rootSetter = rootSetter;
    }

    @SneakyThrows
    @Override
    public void nextFrame(double secondsSinceLastFrame) {
        if (paused) {
            secondsSinceLastFrame = 0;
            paused = false;
        } else if (gameController.checkEndGame(deathPlayer)) {
            stop();
            gameOverUI = new GameOverUI(gameController);
            gameOverUI.display(rootSetter, pane);
        }

        gameController.update(secondsSinceLastFrame);
    }

    public void loadController(Player[] players, KeyTracker keyTracker, ImageLoader imageLoader, Pane pane, AsteroidController asteroidController) {
        gameController = new GameController(players, keyTracker, pane, asteroidController, deathPlayer);
    }
}
