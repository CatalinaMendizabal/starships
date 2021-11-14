import UI.IntroGameUI;
import UI.IntroductionButton;
import UI.LoadGameUI;
import collider.Collider2;
import controller.AsteroidController;
import edu.austral.dissis.starships.collision.CollisionEngine;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.*;
import factory.AsteroidFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.NonNull;
import lombok.Setter;
import model.components.data.AsteroidDTO;
import model.Player;
import model.components.data.PlayerDTO;
import model.components.Asteroid;
import utils.Config;
import model.serializer.GameSerializer;
import model.serializer.GameState;
import utils.KeyConfiguration;
import utils.PlayerManagement;
import utils.SpawnAsteroids;

import java.io.IOException;
import java.util.Objects;
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

    private Parent loadIntro(GameState gameState) throws IOException {
        Pane pane = new Pane();
        pane.setPrefSize(1920, 1080);

        IntroGameUI introGameUI = new IntroGameUI();

        introGameUI.getStart().setOnMouseClicked(event -> {
            try {
                rootSetter.setRoot(loadGame(null));
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

        Player [] players;
        AsteroidController asteroidController;
        KeyConfiguration keyConfiguration = new KeyConfiguration();

        if (gameState == null) {
            players = new Player[Config.PLAYERS];
            keyConfiguration.configKeys(players, pane);
            asteroidController = new AsteroidController();
        } else {
            players = gameState.getPlayers().stream().map(PlayerDTO::toPlayer).toArray(Player[]::new);
            keyConfiguration.configSaveGameKeys(players, pane);
            asteroidController = new AsteroidController(gameState.getAsteroids().stream().map(AsteroidDTO::toAsteroid).collect(Collectors.toList()));
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
            mainTimer = new MainTimer(players, context.getKeyTracker(), imageLoader, pane, asteroidController);
        mainTimer.setPlayers(players);
        mainTimer.setKeyTracker(context.getKeyTracker());
        mainTimer.setImageLoader(imageLoader);
        mainTimer.setPane(pane);
        mainTimer.setAsteroidController(asteroidController);
    }

     /*private Parent endGame(GameState gameState) throws IOException {
        Pane pane = new Pane();
        Text text = new Text("Game over!");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        text.setFill(Color.DARKRED);
        text.setEffect(new DropShadow(20, Color.BLACK));
        text.setX(70);
        text.setY(100);

        Text player = new Text("Player: 1 won :)");
        player.setX(70);
        player.setY(150);

        pane.getChildren().addAll(text, player);

        return pane;
    }*/
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

    boolean paused = false;

    public MainTimer(Player[] players, KeyTracker keyTracker, ImageLoader imageLoader, Pane pane, AsteroidController asteroidController) {
        this.players = players;
        this.keyTracker = keyTracker;
        this.imageLoader = imageLoader;
        this.pane = pane;
        this.asteroidController = asteroidController;
    }

    @Override
    public void nextFrame(double secondsSinceLastFrame) {
        if (paused) {
            secondsSinceLastFrame = 0;
            paused = false;
        }
        pane.requestFocus();
        playerManagement.updatePosition(secondsSinceLastFrame, players, pane, keyTracker, collisionEngine, asteroidController);
        playerManagement.updateHealths(players);
        playerManagement.updateDeaths(players, pane, asteroidController);
        spawnAsteroids.spawnAsteroid(asteroidFactory, asteroidController, imageLoader, pane);
    }

}
