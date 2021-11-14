package model.components.data;

import javafx.scene.shape.Circle;
import lombok.Builder;
import lombok.Data;
import model.components.Bullet;

import java.io.Serializable;

@Data
@Builder
public class BulletData implements Serializable {
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
