package model.components.data;

import controller.ShipController;
import javafx.scene.input.KeyCode;
import lombok.Builder;
import lombok.Data;
import model.Player;

import java.io.Serializable;

@Data
@Builder
public class PlayerData implements Serializable {
    private int id;
    private int score;
    private int lives;
    private ShipControllerData shipController;

    private KeyCode forward;
    private KeyCode left;
    private KeyCode backward;
    private KeyCode right;
    private KeyCode shoot;
    private KeyCode changeShootingMode;

    private boolean isNormalShooting;

    public Player toPlayer() {
        ShipController shipController = this.shipController.toShipController();
        shipController.getShipView().updatePoints(score);

        Player player = Player.builder()
                .id(id)
                .score(score)
                .lives(lives)
                .shipController(shipController)
                .forward(forward)
                .rotateLeft(left)
                .backward(backward)
                .rotateRight(right)
                .shoot(shoot)
                .changeShootingMode(changeShootingMode)
                .isNormalShooting(isNormalShooting)
                .build();

        shipController.getBulletController().getBullets().forEach(bullet -> bullet.setBulletManager(player));
        return player;
    }
}
