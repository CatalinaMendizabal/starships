package model;

import edu.austral.dissis.starships.game.KeyTracker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.weapon.MultipleShooting;
import model.weapon.SingleShooting;

import java.io.Serializable;
import java.util.List;

public class PlayerInput implements Serializable {
    private final Player player;

    public PlayerInput(Player player) {
        this.player = player;
    }

    public void updateInput(Pane pane, KeyTracker keyTracker, double lastFrame) {
        keyTracker.getKeySet().forEach(keyCode -> {
            if (keyCode == player.getForward()) player.getShipController().moveForward(lastFrame, pane);
            else if (keyCode == player.getBackward()) player.getShipController().moveBackward(lastFrame, pane);
            else if (keyCode == player.getRotateLeft()) player.getShipController().rotateLeft(lastFrame);
            else if (keyCode == player.getRotateRight()) player.getShipController().rotateRight(lastFrame);
            else if (keyCode == player.getChangeShootingMode()) {
                player.setNormalShooting(!player.isNormalShooting());
                if (player.isNormalShooting())
                    player.getShipController().getShip().setShootingStrategy(new SingleShooting());
                else player.getShipController().getShip().setShootingStrategy(new MultipleShooting());
            }
            else if (keyCode == player.getShoot()) {
                player.getShipController().fire(player);
                List<ImageView> imageViews = player.getShipController().getBulletController().renderBullets();
                pane.getChildren().addAll(imageViews);
            }
        });
    }
}
