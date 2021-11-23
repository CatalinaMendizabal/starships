package collider;

import model.entity.Asteroid;
import model.entity.weapon.Bullet;
import model.entity.Ship;

public interface EntityCollision {
    void handleCollisionWith(Collisionable collider);

    void handleCollisionWith(Asteroid asteroid);

    void handleCollisionWith(Ship ship);

    void handleCollisionWith(Bullet bullet);
}
