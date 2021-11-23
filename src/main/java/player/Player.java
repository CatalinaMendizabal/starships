package player;

import controller.ShipController;
import data.SavePlayer;
import edu.austral.dissis.starships.game.KeyTracker;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Asteroid;
import model.Ship;
import model.weapon.BulletManager;
import strategy.NormalShooting;
import strategy.MultipleShooting;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class Player implements Serializable, BulletManager {
    private int id;
    private int score;
    private int lives;
    private ShipController shipController;

    private KeyCode keyForward;
    private KeyCode keyRotateLeft;
    private KeyCode keyBackward;
    private KeyCode keyRotateRight;
    private KeyCode keyShoot;
    private KeyCode changeShootingMode;

    boolean isNormalShooting;

    public void updateInput(Pane pane, KeyTracker keyTracker, double secondsSinceLastFrame) {
        if(isDead()) return;
        keyTracker.getKeySet().forEach(keyCode -> {
            if (keyCode == keyForward) shipController.forward(secondsSinceLastFrame, pane);
            else if (keyCode == keyBackward) shipController.backward(secondsSinceLastFrame, pane);
            else if (keyCode == keyRotateLeft) shipController.rotateLeft(secondsSinceLastFrame);
            else if (keyCode == keyRotateRight) shipController.rotateRight(secondsSinceLastFrame);
            else if (keyCode == keyShoot) shipController.fire(this);
            else if (keyCode == changeShootingMode) {
                setNormalShooting(!isNormalShooting);
                if (isNormalShooting) {shipController.getShip().setShooting(new NormalShooting());}
                else shipController.getShip().setShooting(new MultipleShooting());
            }
        });
    }

    @Override
    public void bulletEntity(double damage, Asteroid asteroid) {
        score += damage;
        if(asteroid.shouldBeRemoved()) score += damage;
        shipController.getShipView().updatePoints(score);
    }

    @Override
    public void bulletEntity(double damage, Ship ship) {
        score += damage / 10;
        if(ship.shouldBeRemoved()) score += damage / 10;
        shipController.getShipView().updatePoints(score);
    }

    @Override
    public boolean shipBullet(Ship ship) {return ship.equals(shipController.getShip());}

    public boolean isDead() {return shipController.getShip().getHealth() <= 0 && lives <= 0;}

    public void updateDeath() {
        if (shipController.getShip().shouldBeRemoved())
            lives--;
    }

    public boolean isShipDead() {return shipController.getShip().getHealth() <= 0;}

    public void updateShipStyle(String shipName) {shipController.updateShipStyle(shipName);}

    public SavePlayer toDTO() {
        return SavePlayer.builder()
                .id(id)
                .score(score)
                .lives(lives)
                .shipController(shipController.saveData())
                .keyForward(keyForward)
                .keyRotateLeft(keyRotateLeft)
                .keyBackward(keyBackward)
                .keyRotateRight(keyRotateRight)
                .keyShoot(keyShoot)
                .changeShootingMode(changeShootingMode)
                .isNormalShooting(isNormalShooting)
                .build();
    }

}
