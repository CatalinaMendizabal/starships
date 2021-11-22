package collider;

import edu.austral.dissis.starships.collision.Collider;
import model.components.Asteroid;
import model.components.Bullet;
import model.components.Ship;

public interface Collisionable extends Collider<Collisionable> {

    void handleCollisionWith(Bullet bullet);

    void handleCollisionWith(Asteroid asteroid);

    void handleCollisionWith(Ship ship);

}
