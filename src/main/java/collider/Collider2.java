package collider;

import edu.austral.dissis.starships.collision.Collider;
import model.entities.Asteroid;
import model.entities.Bullet;
import model.entities.Ship;

public interface Collider2 extends Collider<Collider2> {
    void handleCollisionWith(Asteroid asteroid);

    void handleCollisionWith(Ship ship);

    void handleCollisionWith(Bullet bullet);

}
