package model.components.data;


import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import lombok.Builder;
import lombok.Data;
import model.components.Asteroid;

import java.io.Serializable;


@Data
@Builder
public class AsteroidData implements Serializable {
    private double health;
    private Vector2 position;
    private Vector2 direction;
    private double speed;
    private double centerX;
    private double centerY;
    private double rotate;
    private double radius;

    public Asteroid toAsteroid() {
        Circle shape = new Circle(radius);
        shape.setCenterX(centerX);
        shape.setCenterY(centerY);
        shape.setRotate(rotate);
        return new Asteroid(position, direction, 1/health * 10000,  health, health);
    }
}
