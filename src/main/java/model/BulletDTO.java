package model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.Builder;
import lombok.Data;
import model.entities.Bullet;

import java.io.Serializable;

@Data
@Builder
public class BulletDTO implements Serializable {
    double posX;
    double posY;
    double rotate;
    double radius;
    double speed;
    double damage;

    public Bullet toBullet() {
        Circle shape = new Circle(radius);
        shape.setRotate(rotate);
        shape.setLayoutX(posX);
        shape.setLayoutY(posY);
        return new Bullet(shape, speed, damage, null);
    }
}