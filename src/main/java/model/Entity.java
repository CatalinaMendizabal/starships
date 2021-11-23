package model;

import collider.EntityCollision;
import edu.austral.dissis.starships.vector.Vector2;
import view.EntityView;

public interface Entity extends EntityCollision {
    Vector2 getPosition();
    double getDirection();
    double getSpeed();
    void move(Vector2 to);

    boolean shouldBeRemoved();

    void accept(EntityView visitor);

/*    @Override
    default void handleCollisionWith(Asteroid asteroid) {}
    @Override
    default void handleCollisionWith(Ship ship) {}
    @Override
    default void handleCollisionWith(Bullet bullet) {}*/
}
