package controller;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.Player;
import model.components.data.ShipControllerData;
import model.components.Ship;
import view.ShipView;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
public class ShipController implements Serializable {

    private final MovementController movementController = new MovementController(this);
    private ShipView shipView;
    private Ship ship;
    private BulletController bulletController;

    public void fire(Player shooter) {ship.fire(shooter).forEach(bulletController::addBullet);}

    public void updateHealth() {shipView.updateHealth(ship.getHealth());}

    public void moveForward(Double lastFrame, Pane pane) {movementController.moveForward(lastFrame, pane);}

    public void moveBackward(Double lastFrame, Pane pane) {movementController.moveBackward(lastFrame, pane);}

    public void rotateLeft(Double lastFrame) {movementController.rotateLeft(lastFrame);}

    public void rotateRight(Double lastFrame) {movementController.rotateRight(lastFrame);}

    public void updateShipStyle(String shipName) {shipView.updateShipStyle(shipName);}

    public ImageView deathControl() {
        if (ship.getHealth() <= 0) {
            shipView.getHealthView().setVisible(false);
            shipView.getScore().setVisible(false);
            return shipView.getImageView();
        } else return null;
    }

    public ShipControllerData buildData() {
        return ShipControllerData.builder()
                .ship(ship.buildData())
                .bulletController(bulletController.buildData())
                .build();
    }
}
