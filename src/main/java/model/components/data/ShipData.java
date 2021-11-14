package model.components.data;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.Builder;
import lombok.Data;
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
    private double posX;
    private double posY;
    private double speed;
    private double angle;
    private Shooting shootingStrategy;

    public Ship toShip() {
        Shape shape = new Rectangle(70, 45);
        shape.setLayoutX(posX);
        shape.setLayoutY(posY);
        shape.setRotate(angle);

        Ship ship = Ship.builder()
                .health(health)
                .shape(shape)
                .speed(speed)
                .shootingStrategy(shootingStrategy)
                .build();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ship.setShootingStrategy(new SingleShooting());
            }
        } , 5000);

        return ship;
    }
}
