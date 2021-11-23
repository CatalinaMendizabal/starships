package collider;

import model.Asteroid;
import model.weapon.Bullet;
import model.Ship;

public interface EntityCollision {
    void handleCollisionWith(Collisionable collider);

    void handleCollisionWith(Asteroid asteroid);

    void handleCollisionWith(Ship ship);

    void handleCollisionWith(Bullet bullet);
}
