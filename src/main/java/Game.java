import UI.IntroductionButton;
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
import model.Player;
import model.entities.Asteroid;
import utils.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    GameTimer gameTimer;
    MainTimer mainTimer;

    public GameManager(RootSetter rootSetter, GameContext gameContext) {
        this.rootSetter = rootSetter;
        this.context = gameContext;
    }

    boolean isIntro = true;

    Parent init() throws IOException {
        Parent parent = isIntro ? loadIntro() : loadGame();
        return parent;
    }

    private Parent loadIntro() throws IOException {
        Pane pane = new Pane();
        pane.setPrefSize(1920, 1080);

        ImageLoader imageLoader = new ImageLoader();
        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        ImageView imageView = new ImageView(background);
        imageView.setFitWidth(1920);
        imageView.setFitHeight(1080);

        pane.getChildren().add(imageView);

        Text text = generateTitle();
        Button start = new IntroductionButton("Start Game", 100, 150).getButton();
        Button loaded = new IntroductionButton("Continue Game", 100, 230).getButton();
        Button exit = new IntroductionButton("Exit Game", 100, 320).getButton();

        start.setOnMouseClicked(event -> {
            try {
                rootSetter.setRoot(loadGame());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        pane.getChildren().addAll(text, start, loaded, exit);
        return pane;
    }

    private Text generateTitle() {
        Text text = new Text("Welcome to STARSHIPS");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(20, Color.BLACK));
        text.setX(70);
        text.setY(100);
        return text;
    }


    private Parent loadGame() throws IOException {

        ImageLoader imageLoader = new ImageLoader();

        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

        Pane pane = new Pane();
        pane.setBackground(new Background(backgroundImage));

        Player[] players = new Player[Config.PLAYERS];
        for (int i = 0; i < Config.PLAYERS; i++) {

            players[i] = new Player(i, 0, Config.LIVES, Objects.requireNonNull(Config.getPlayerShips())[i],
                    Config.PLAYER_KEYS[i][0],
                    Config.PLAYER_KEYS[i][1],
                    Config.PLAYER_KEYS[i][2],
                    Config.PLAYER_KEYS[i][3],
                    Config.PLAYER_KEYS[i][4]);

            pane.getChildren().add(players[i].getShipController().getShipView().getImageView());
            pane.getChildren().add(players[i].getShipController().getShipView().getHealthView());
            pane.getChildren().add(players[i].getShipController().getShipView().getPoints());
        }

        AsteroidController asteroidController = new AsteroidController();
       // PickupController pickupController = new PickupController();
        if (mainTimer == null) mainTimer = new MainTimer(players, context.getKeyTracker(), imageLoader, pane, asteroidController);
        mainTimer.setPlayers(players);
        mainTimer.setKeyTracker(context.getKeyTracker());
        mainTimer.setImageLoader(imageLoader);
        mainTimer.setPane(pane);
        mainTimer.setAsteroidController(asteroidController);

        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                isIntro = !isIntro;
                try {
                    mainTimer.stop();
                    mainTimer.setPaused(true);
                    rootSetter.setRoot(loadIntro());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        mainTimer.start();
        return pane;
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
        if(paused) {
            secondsSinceLastFrame = 0;
            paused = false;
        }
        pane.requestFocus();
        updatePosition(secondsSinceLastFrame);
        updateHealths();
        updateDeaths();
        spawnAsteroid();
    }

    private void updateHealths() {
        for (Player player : players) {
            player.getShipController().updateHealth();
        }
    }

    private void spawnAsteroid() {
        if(Math.random() * 100.0 < 5) {
            Asteroid asteroid = asteroidFactory.createAsteroid();
            ImageView imageView = asteroidController.spawnAsteroid(asteroid, imageLoader, pane.getWidth(), pane.getHeight());
            pane.getChildren().add(imageView);
        }
    }

    private void updatePosition(Double secondsSinceLastFrame) {

        for(Player player : players) {
            player.updateInput(pane, keyTracker, secondsSinceLastFrame);
            player.getShipController().getBulletController().updatePositions(secondsSinceLastFrame);
        }

        asteroidController.updatePositions(secondsSinceLastFrame);

        List<Collider2> colliders = new ArrayList<>();
        colliders.addAll(asteroidController.getAsteroids());
        for (Player player : players) {
            colliders.add(player.getShipController().getShip());
            colliders.addAll(player.getShipController().getBulletController().getBullets());
        }
        collisionEngine.checkCollisions(colliders);
    }

    private void updateDeaths() {
        for(Player player : players) {
            pane.getChildren().remove(player.getShipController().updateDeath());
            pane.getChildren().removeAll(player.getShipController().getBulletController().removeDeadBullets(pane.getWidth(), pane.getHeight()));
        }
        asteroidController.killOutOfBounds(pane.getWidth(), pane.getHeight());
        pane.getChildren().removeAll(asteroidController.updateDeaths());
    }
}
