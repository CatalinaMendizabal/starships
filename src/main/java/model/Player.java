package model;

import controller.ShipController;
import edu.austral.dissis.starships.game.KeyTracker;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.components.data.PlayerData;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class Player implements Serializable {
    private final PlayerInput playerInput = new PlayerInput(this);
    private int id;
    private int score;
    private int lives;
    private ShipController shipController;

    public KeyCode forward;
    public KeyCode backward;
    public KeyCode rotateLeft;
    public KeyCode rotateRight;
    public KeyCode shoot;
    public KeyCode changeShootingMode;

    boolean isNormalShooting;

    public void updateInput(Pane pane, KeyTracker keyTracker, double secondsSinceLastFrame) {playerInput.updateInput(pane, keyTracker, secondsSinceLastFrame);}

    public PlayerData buildData() {
        return PlayerData.builder()
                .id(id)
                .score(score)
                .lives(lives)
                .shipController(shipController.buildData())
                .forward(forward)
                .left(rotateLeft)
                .backward(backward)
                .right(rotateRight)
                .shoot(shoot)
                .changeShootingMode(changeShootingMode)
                .isNormalShooting(isNormalShooting)
                .build();
    }

    public void addPoints(double points) {
        score += points;
        updatePoints();
    }

    public void updatePoints() {
        shipController.getShipView().updatePoints(score);
    }
}
