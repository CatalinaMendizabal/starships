package utils;

import collider.Collisionable;
import controller.AsteroidController;
import edu.austral.dissis.starships.collision.CollisionEngine;
import edu.austral.dissis.starships.game.KeyTracker;
import javafx.scene.layout.Pane;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManagement {

    public void updateDeathPlayers(Player[] players, Boolean[] deathPlayers) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getShipController().getShip().getHealth() <= 0)  deathPlayers[i] = true;
        }
    }

    public boolean checkEndGame( Boolean[] deathPlayers) {
        boolean status = true;
        for (Boolean deathPlayer : deathPlayers) {
            if (!deathPlayer) {
                status = false;
                break;
            }
        }
        return status;
    }

    public void updateHealths(Player[] players) {
        for (Player player : players) {
            if (player.getShipController().getShip().getHealth() > 0) player.getShipController().updateHealth();
        }
    }

    public void updatePosition(Double lastFrame, Player[] players, Pane pane, KeyTracker keyTracker, CollisionEngine collisionEngine, AsteroidController asteroidController) {
        for (Player player : players) {
            player.updateInput(pane, keyTracker, lastFrame);
            player.getShipController().getBulletController().updatePos(lastFrame);
        }

        asteroidController.updatePos(lastFrame);

        List<Collisionable> colliders = new ArrayList<>(asteroidController.getAsteroids());
        for (Player player : players) {
            colliders.add(player.getShipController().getShip());
            colliders.addAll(player.getShipController().getBulletController().getBullets());
        }
        collisionEngine.checkCollisions(colliders);
    }

    public void updateDeaths(Player[] players, Pane pane, AsteroidController asteroidController) {
        for (Player player : players) {
            pane.getChildren().remove(player.getShipController().deathControl());
            pane.getChildren().removeAll(player.getShipController().getBulletController().deathControl(pane.getWidth(), pane.getHeight()));
        }
        asteroidController.removeAsteroids(pane.getWidth(), pane.getHeight());
        pane.getChildren().removeAll(asteroidController.deathControl());
    }
}
