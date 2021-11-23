package model.entity;

import collider.Collisionable;
import collider.EntityCollision;
import data.SaveAsteroid;
import edu.austral.dissis.starships.vector.Vector2;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.entity.weapon.Bullet;
import view.EntityView;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Asteroid implements Serializable, Entity, EntityCollision {

    Double health;
    Vector2 position;
    double direction;
    double speed;

    public void handleCollisionWith(Collisionable collider) {
        collider.getEntity().handleCollisionWith(this);
    }

    @Override
    public void handleCollisionWith(Asteroid asteroid) {}

    @Override
    public void handleCollisionWith(Ship ship) {}

    public void move(Vector2 to) {
        position = to;
    }

    public void handleCollisionWith(Bullet bullet) {
        health = health - bullet.getDamage();
    }

    public SaveAsteroid toDTO() {
        return SaveAsteroid.builder()
                .health(health)
                .posX(position.getX())
                .posY(position.getY())
                .rotate(direction)
                .size(health)
                .speed(speed)
                .build();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public boolean shouldBeRemoved() {
        return health <= 0;
    }

    @Override
    public void accept(EntityView visitor) {
        visitor.visitAsteroid(this);
    }
}
