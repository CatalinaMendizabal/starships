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

    private KeyCode keyForward;
    private KeyCode keyRotateLeft;
    private KeyCode keyBackward;
    private KeyCode keyRotateRight;
    private KeyCode keyShoot;
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
                .keyForward(keyForward)
                .keyRotateLeft(keyRotateLeft)
                .keyBackward(keyBackward)
                .keyRotateRight(keyRotateRight)
                .keyShoot(keyShoot)
                .changeShootingMode(changeShootingMode)
                .isNormalShooting(isNormalShooting)
                .build();

        shipController.getBulletController().getBullets().forEach(bullet -> bullet.setShooter(player));
        return player;
    }
}
