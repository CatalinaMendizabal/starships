package model.entity.weapon;

import collider.Collisionable;
import collider.EntityCollision;
import data.SaveBullet;
import edu.austral.dissis.starships.vector.Vector2;
import lombok.Data;
import model.entity.Asteroid;
import model.entity.Entity;
import model.entity.Ship;
import view.EntityView;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bullet implements Entity, Observable, EntityCollision {

    Vector2 position;
    double direction;
    double speed;
    double damage;

    List<BulletManager> observers = new ArrayList<>();

    public Bullet(Vector2 position, double direction, double speed, double damage) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
    }

    public void handleCollisionWith(Collisionable collider) {
        collider.getEntity().handleCollisionWith(this);
    }

    public void move(Vector2 to) {
        position = to;
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
    public void handleCollisionWith(Asteroid asteroid) {
        speed = 0;
        if (observers != null) {
            for (BulletManager observer : observers) {
                observer.bulletEntity(damage, asteroid);
            }
        }
    }

    @Override
    public void handleCollisionWith(Ship ship) {}

    @Override
    public void handleCollisionWith(Bullet bullet) {}


    public SaveBullet toDTO() {
        return SaveBullet.builder()
                .speed(speed)
                .damage(damage)
                .posX(position.getX())
                .posY(position.getY())
                .rotate(direction)
                .radius(5)
                .build();
    }

    @Override
    public boolean shouldBeRemoved() {
        return speed == 0;
    }

    @Override
    public void accept(EntityView visitor) {
        visitor.visitBullet(this);
    }

    @Override
    public void attachManager(BulletManager observer) {
        observers.add(observer);
    }

    @Override
    public void detachManager(BulletManager observer) {
        observers.remove(observer);
    }
}
