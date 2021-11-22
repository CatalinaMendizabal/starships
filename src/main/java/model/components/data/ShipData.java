package model.components.data;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.Builder;
import lombok.Data;
import model.components.Asteroid;
import model.components.Ship;
import model.weapon.Shooting;
import model.weapon.SingleShooting;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

@Data
@Builder
public class ShipData implements Serializable {

    private double health;
    private Shooting shootingStrategy;
    private double speed;
    private double posX;
    private double posY;
    private double angle;

    public Ship toShip() {
        Shape shape = new Rectangle(70, 45);
        shape.setRotate(angle);
        shape.setTranslateX(posX);
        shape.setTranslateY(posY);
        shape.setLayoutX(posX);
        shape.setLayoutY(posY);

        Ship ship = new Ship(health, shape, speed);
        ship.setShootingStrategy(shootingStrategy);
        ship.setHealth(health);
        ship.setSpeed(speed);
        ship.setShape(shape);

        return ship;
    }
}
