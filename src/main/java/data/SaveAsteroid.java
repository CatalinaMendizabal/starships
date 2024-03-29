package data;

import edu.austral.dissis.starships.vector.Vector2;
import lombok.Builder;
import lombok.Data;
import model.entity.Asteroid;

import java.io.Serializable;


@Data
@Builder
public class SaveAsteroid implements Serializable {
    private double health;
    private double speed;
    private double posX;
    private double posY;
    private double rotate;
    private double size;

    public Asteroid toAsteroid() {
        return new Asteroid(health, Vector2.vector(posX, posY), rotate, speed);
    }
}
