package controller;

import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class MovementController implements Serializable {
    private final ShipController shipController;

    public MovementController(ShipController shipController) {
        this.shipController = shipController;
    }

    public void moveForward(Double lastFrame, Pane pane) {
        double movement = lastFrame * shipController.getShip().getSpeed();
        Vector2 movementVector = Vector2.vectorFromModule(movement, (Math.toRadians(shipController.getShipView().getRotate()) - Math.PI / 2));
        Vector2 from = Vector2.vector((float) shipController.getShipView().getLayoutX(), (float) shipController.getShipView().getLayoutY());
        moveShip(pane, movementVector, from);
    }

    public void moveBackward(Double lastFrame, Pane pane) {
        double movement = lastFrame * shipController.getShip().getSpeed();
        Vector2 movementVector = Vector2.vectorFromModule(-movement, (Math.toRadians(shipController.getShipView().getRotate()) - Math.PI / 2));
        Vector2 from = Vector2.vector(shipController.getShipView().getLayoutX(), shipController.getShipView().getLayoutY());
        moveShip(pane, movementVector, from);
    }

    public void rotateLeft(Double lastFrame) {
        double movement = lastFrame * shipController.getShip().getSpeed();
        shipController.getShipView().setRotate(shipController.getShipView().getRotate() - movement);
        shipController.getShip().getShape().setRotate(shipController.getShipView().getRotate() - movement);
    }

    public void rotateRight(Double lastFrame) {
        double movement = lastFrame * shipController.getShip().getSpeed();
        shipController.getShipView().setRotate(shipController.getShipView().getRotate() + movement);
        shipController.getShip().getShape().setRotate(shipController.getShipView().getRotate() + movement);
    }

    void moveShip(Pane pane, Vector2 movementVector, Vector2 from) {
        Vector2 to = from.add(movementVector);
        if (to.getX() > 0 && to.getX() < pane.getWidth() - 100 && to.getY() > 0 && to.getY() < pane.getHeight() - 100) {
            shipController.getShipView().move(to);
            shipController.getShip().move(to);
        }
    }
}
