package controller;

import collider.ColliderEntity;
import collider.ColliderManager;
import collider.Collisionable;
import edu.austral.dissis.starships.collision.CollisionEngine;
import edu.austral.dissis.starships.game.KeyTracker;
import edu.austral.dissis.starships.vector.Vector2;
import factory.AsteroidFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Asteroid;
import model.Entity;
import player.Player;
import view.ImageRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {
    Player[] players;
    AsteroidController asteroidController;
    KeyTracker keyTracker;
    Pane pane;
    AsteroidFactory asteroidFactory = new AsteroidFactory();
    CollisionEngine collisionEngine = new CollisionEngine();
    ColliderManager colliderManager = new ColliderManager();
    ImageRenderer imageRenderer;

    public GameController(Player[] players, KeyTracker keyTracker, Pane pane, AsteroidController asteroidController) {
        this.players = players;
        this.keyTracker = keyTracker;
        this.pane = pane;
        this.asteroidController = asteroidController;
        this.imageRenderer = new ImageRenderer(pane);
    }

    public void update(double secondsSinceLastFrame) {
        pane.requestFocus();

        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.stream(players).map(Player::getShipController).map(ShipController::getBulletController).map(BulletController::getBullets).flatMap(Collection::stream).collect(Collectors.toList()));
        entities.addAll(asteroidController.getAsteroids());

        updatePosition(entities, secondsSinceLastFrame);

        entities.addAll(Arrays.stream(players).map(Player::getShipController).map(ShipController::getShip).collect(Collectors.toList()));

        List<ColliderEntity> colliderEntities = colliderManager.generateColliders(entities);

        collisionEngine.checkCollisions(colliderEntities.stream().map(e -> (Collisionable) e).collect(Collectors.toList()));

        imageRenderer.renderObjects(colliderEntities.stream().map(ColliderEntity::getEntity).collect(Collectors.toList()));

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
            Asteroid asteroid = asteroidFactory.createAsteroid(pane.getWidth(), pane.getHeight());
            asteroidController.spawnAsteroid(asteroid);
        }
    }

    private void updatePosition(List<Entity> entities, Double secondsSinceLastFrame) {
        for(Player player : players) {
            player.updateInput(pane, keyTracker, secondsSinceLastFrame);
        }
        for (Entity entity : entities) {
            Vector2 movementVector = Vector2.vectorFromModule((entity.getSpeed() * secondsSinceLastFrame), entity.getDirection());
            Vector2 from = entity.getPosition();
            Vector2 to = from.add(movementVector);
            entity.move(to);
        }
    }

    private void updateDeaths() {
        for(Player player : players) {
            if(player.isShipDead()) {
                 player.updateDeath();
            }
            player.getShipController().getBulletController().removeDeadBullets(pane.getWidth(), pane.getHeight());
        }
        asteroidController.killOutOfBounds(pane.getWidth(), pane.getHeight());
        asteroidController.updateDeaths();
    }

    public boolean isOver() {return Arrays.stream(players).allMatch(Player::isDead);}

}
