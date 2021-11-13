package controller;

import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.Player;
import model.ShipControllerDTO;
import model.entities.Ship;
import model.weapon.MultipleShooting;
import model.weapon.SingleShooting;
import view.ShipView;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
public class ShipController implements Serializable {
    private ShipView shipView;
    private Ship ship;
    private BulletController bulletController;

    public void moveForward(Double secondsSinceLastFrame, Pane pane) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        Vector2 movementVector = Vector2.vectorFromModule(movement, (Math.toRadians(shipView.getRotate()) - Math.PI / 2));
        Vector2 from = Vector2.vector((float) shipView.getLayoutX(), (float) shipView.getLayoutY());
        moveShip(pane, movementVector, from);
    }

    public void moveBackward(Double secondsSinceLastFrame, Pane pane) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        Vector2 movementVector = Vector2.vectorFromModule(-movement, (Math.toRadians(shipView.getRotate()) - Math.PI / 2));
        Vector2 from = Vector2.vector(shipView.getLayoutX(), shipView.getLayoutY());
        moveShip(pane, movementVector, from);
    }

    public void rotateLeft(Double secondsSinceLastFrame) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        shipView.setRotate(shipView.getRotate() - movement);
        ship.getShape().setRotate(shipView.getRotate() - movement);
    }

    public void rotateRight(Double secondsSinceLastFrame) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        shipView.setRotate(shipView.getRotate() + movement);
        ship.getShape().setRotate(shipView.getRotate() + movement);
    }

    public ImageView updateDeath() {
        if (ship.getHealth() <= 0) {
            shipView.getHealthView().setVisible(false);
            shipView.getPoints().setVisible(false);
            return shipView.getImageView();
        } else return null;
    }

    public void fire(Player shooter, boolean isNormalShooting) {
        //if (isNormalShooting) ship.setShootingStrategy(new SingleShooting());
        //ship.setShootingStrategy(new MultipleShooting());
        ship.fire(bulletController, shooter);
    }

    private void moveShip(Pane pane, Vector2 movementVector, Vector2 from) {
        Vector2 to = from.add(movementVector);

        if (to.getX() > 0 && to.getX() < pane.getWidth() - 100 && to.getY() > 0 && to.getY() < pane.getHeight() - 100) {
            shipView.move(to);
            ship.move(to);
        }
    }
    public void updateHealth() {
        shipView.updateHealth(ship.getHealth());
    }

      public ShipControllerDTO toDTO() {
        return ShipControllerDTO.builder()
                .imageName("starship.gif")
                .ship(ship.toDTO())
                .bulletController(bulletController.toDTO())
                .build();
    }
}
