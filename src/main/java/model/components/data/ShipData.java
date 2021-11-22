/*
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
    private Shape shape;
    private double speed;
    private float angle;
    private Shooting shooting;

    public Ship toShip() {

        */
/*
         .health(health)
                .shape(shape)
                .shooting(shooting)
                .speed(speed)
                .posX(shape.getLayoutX())
                .posY(shape.getLayoutY())
                .angle(shape.getRotate())
         *//*

        Ship ship = Ship.builder()
                .health(health)
                .shape(shape)
                .speed(speed)
                .directionAngle(angle)
                .shooting(shooting)
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
*/
