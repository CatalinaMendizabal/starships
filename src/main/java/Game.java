import UI.IntroductionButton;
import collider.Collider2;
import controller.AsteroidController;
import controller.BulletController;
import controller.ShipController;
import edu.austral.dissis.starships.collision.CollisionEngine;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.*;
import factory.AsteroidFactory;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.NonNull;
import model.Asteroid;
import model.Bullet;
import model.Ship;
import model.SingleShooting;
import view.ShipView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    GameState gameState;
    public GameManager(RootSetter rootSetter, GameContext gameContext) {
        this.rootSetter = rootSetter;
        this.context = gameContext;
    }

    boolean isIntro = true;

    public static Object getInstance() {
        return GameManager.class;
    }

    public GameState getGameState() {return gameState;}

    public void setGameState(GameState gameState) {this.gameState = gameState;}

    Parent init() throws IOException {
        Parent parent = isIntro ? loadIntro() : loadGame();
        return parent;
    }

    private Parent loadIntro() throws IOException {
        Pane pane = new Pane();
        gameState = GameState.GAME_OVER;
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

        exit.setOnMouseClicked(event -> {System.exit(0);});

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
        gameState = GameState.PLAYING;

        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

        Image image = imageLoader.loadFromResources("starship.png", 100.0, 100.0);
        ShipView shipView = new ShipView(image, 200, 200);
        Ship ship = new Ship(200.0, new SingleShooting(), new Rectangle(70, 45));
        ShipController shipController = new ShipController(shipView, ship);

        Pane pane = new Pane(shipView.getImageView());
        pane.setBackground(new Background(backgroundImage));

        MainTimer timer = new MainTimer(shipController, new AsteroidController(), new BulletController(), context.getKeyTracker(), imageLoader, pane);
        timer.start();

      /*  pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                isIntro = !isIntro;
                try {
                    timer.stop();
                    rootSetter.setRoot(init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

        return pane;
    }
}

class MainTimer extends GameTimer {
    ShipController shipController;
    AsteroidController asteroidController;
    BulletController bulletController;
    KeyTracker keyTracker;
    ImageLoader imageLoader;
    Pane pane;
    AsteroidFactory asteroidFactory = new AsteroidFactory();
    CollisionEngine collisionEngine = new CollisionEngine();

    public MainTimer(ShipController shipController, AsteroidController asteroidController, BulletController bulletController, KeyTracker keyTracker, ImageLoader imageLoader, Pane pane) {
        this.shipController = shipController;
        this.keyTracker = keyTracker;
        this.asteroidController = asteroidController;
        this.imageLoader = imageLoader;
        this.pane = pane;
        this.bulletController = bulletController;
    }

    @Override
    public void nextFrame(double secondsSinceLastFrame) {
        updatePosition(secondsSinceLastFrame);
        updateDeaths();
        spawnAsteroid();
    }

    private void spawnAsteroid() {
        if (Math.random() * 100.0 < 5) {
            Asteroid asteroid = asteroidFactory.createAsteroid();
            ImageView imageView = asteroidController.spawnAsteroid(asteroid, imageLoader, pane.getWidth(), pane.getHeight());
            pane.getChildren().add(imageView);
        }
    }

    private void updatePosition(Double secondsSinceLastFrame) {
        double movement = 100 * secondsSinceLastFrame;

        gameInput(movement);

        bulletController.updatePositions(secondsSinceLastFrame);
        asteroidController.updatePositions(secondsSinceLastFrame);

        addCollisions();
    }

    private void addCollisions() {
        List<Asteroid> asteroids = asteroidController.getAsteroids();
        List<Bullet> bullets = bulletController.getBullets();
        List<Collider2> colliders = new ArrayList<>(asteroids);
        colliders.addAll(bullets);
        colliders.add(shipController.getShip());
        collisionEngine.checkCollisions(colliders);
    }

    private void gameInput(double movement) {
        keyTracker.getKeySet().forEach(keyCode -> {
            switch (keyCode) {
                case UP:
                    shipController.moveForward(movement);
                    break;
                case DOWN:
                    shipController.backward(movement);
                    break;
                case LEFT:
                    shipController.rotateLeft(movement);
                    break;
                case RIGHT:
                    shipController.rotateRight(movement);
                    break;
                case P:
                    // pause game
                    break;
                case SPACE: {
                    shipController.fire(bulletController);
                    List<ImageView> imageViews = bulletController.renderBullets(imageLoader);
                    pane.getChildren().addAll(imageViews);
                    break;
                }
                case ESCAPE: System.exit(0);
                default:
                    break;
            }
        });
    }

    private void updateDeaths() {
        pane.getChildren().remove(shipController.updateDeath());
        asteroidController.deleteOutOfScreen(pane.getWidth(), pane.getHeight());
        pane.getChildren().removeAll(asteroidController.updateDeaths());
        pane.getChildren().removeAll(bulletController.removeDeadBullets(pane.getWidth(), pane.getHeight()));
    }
}
