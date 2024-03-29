package controller;

import data.SaveShipController;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.entity.weapon.Bullet;
import model.entity.Ship;
import model.Player;
import view.entity.ShipView;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ShipController implements Serializable {
    private ShipView shipView;
    private Ship ship;
    private BulletController bulletController;

    public void forward(Double secondsSinceLastFrame, Pane pane) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        Vector2 movementVector = Vector2.vectorFromModule(movement, Math.toRadians(ship.getDirection()) - Math.PI / 2);
        Vector2 from = Vector2.vector(ship.getPosition().getX(), ship.getPosition().getY());
        Vector2 to = from.add(movementVector);
        if (checkScreen(pane, to)) moveShip(to);

    }

    private boolean checkScreen(Pane pane, Vector2 to) {
        return to.getX() > 0 && to.getX() < pane.getWidth() - shipView.getWidth() && to.getY() > 0 && to.getY() < pane.getHeight() - shipView.getHeight();
    }

    public void backward(Double secondsSinceLastFrame, Pane pane) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        Vector2 movementVector = Vector2.vectorFromModule(movement, Math.toRadians(ship.getDirection()) + Math.PI / 2);
        Vector2 from = Vector2.vector(ship.getPosition().getX(), ship.getPosition().getY());
        Vector2 to = from.add(movementVector);
        if (checkScreen(pane, to)) moveShip(to);

    }

    public void rotateLeft(Double secondsSinceLastFrame) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        ship.setDirection(ship.getDirection() - movement);
    }

    public void rotateRight(Double secondsSinceLastFrame) {
        double movement = secondsSinceLastFrame * ship.getSpeed();
        ship.setDirection(ship.getDirection() + movement);
    }

    public void fire(Player shooter) {
        List<Bullet> bullets = ship.fire();
        bullets.forEach(bulletController::addBullet);
        bullets.forEach(bullet -> bullet.attachManager(shooter));
    }

    public void moveShip(Vector2 to) {
        ship.move(to);
    }

    public void updateHealth() {
        shipView.updateHealth(ship.getHealth());
    }

    public SaveShipController saveData() {
        return SaveShipController.builder()
                .ship(ship.toDTO())
                .bulletController(bulletController.saveData())
                .build();
    }

    public void updateShipStyle(String shipName) {
        ship.setName(shipName);
        shipView.updateShipStyle(shipName);
    }

}
