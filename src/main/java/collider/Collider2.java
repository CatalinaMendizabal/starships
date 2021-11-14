package collider;

import edu.austral.dissis.starships.collision.Collider;
import model.components.Asteroid;
import model.components.Bullet;
import model.components.Ship;

public interface Collider2 extends Collider<Collider2> {
    void handleCollisionWith(Asteroid asteroid);

    void handleCollisionWith(Ship ship);

    void handleCollisionWith(Bullet bullet);

}
