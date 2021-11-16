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

    public void fire(Player shooter) {ship.fire(bulletController, shooter);}

    public void updateHealth() {shipView.updateHealth(ship.getHealth());}

    public void moveForward(Double secondsSinceLastFrame, Pane pane) {movementController.moveForward(secondsSinceLastFrame, pane);}

    public void moveBackward(Double secondsSinceLastFrame, Pane pane) {movementController.moveBackward(secondsSinceLastFrame, pane);}

    public void rotateLeft(Double secondsSinceLastFrame) {movementController.rotateLeft(secondsSinceLastFrame);}

    public void rotateRight(Double secondsSinceLastFrame) {movementController.rotateRight(secondsSinceLastFrame);}

    public ImageView deathControl() {
        if (ship.getHealth() <= 0) {
            shipView.getHealthView().setVisible(false);
            shipView.getScore().setVisible(false);
            return shipView.getImageView();
        } else return null;
    }

      public ShipControllerData buildData() {
        return ShipControllerData.builder()
                .imageName("starship.png")
                .ship(ship.buildData())
                .bulletController(bulletController.buildData())
                .build();
    }
}
