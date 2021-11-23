package data;

import edu.austral.dissis.starships.vector.Vector2;
import lombok.Builder;
import lombok.Data;
import model.weapon.Bullet;

import java.io.Serializable;

@Data
@Builder
public class SaveBullet implements Serializable {
    double posX;
    double posY;
    double rotate;
    double radius;
    double speed;
    double damage;

    public Bullet toBullet() {
        return new Bullet(Vector2.vector(posX, posY), rotate, speed, damage);
    }
}
