package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.Data;
import model.BulletManager;
import model.components.data.BulletData;
import model.Player;

public class Bullet extends GameObject implements Collisionable {

    BulletManager bulletManager;

    public Bullet(Shape shape, double speed, double damage, BulletManager bulletManager) {
        super(0.0, shape, speed, damage);
        this.bulletManager = bulletManager;
    }

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX());
        shape.setLayoutY(to.getY());
    }

    @Override
    public void handleCollisionWith(Collisionable collider) {
        collider.handleCollisionWith(this);
    }

    @Override
    public void handleCollisionWith(Ship ship) {}

    @Override
    public void handleCollisionWith(Bullet bullet) {}

    @Override
    public void handleCollisionWith(Asteroid asteroid) {
        speed = 0;
        if (asteroid.getHealth() < 0) bulletManager.addPoints(damage);
        bulletManager.addPoints(damage);
    }

    public double getDamage() {return damage;}

    public BulletManager getBulletManager() {return bulletManager;}

    public void setBulletManager(BulletManager bulletManager) {this.bulletManager = bulletManager;}

    public BulletData buildData() {
        return BulletData.builder()
                .speed(speed)
                .damage(damage)
                .posX(shape.getLayoutX())
                .posY(shape.getLayoutY())
                .rotate(shape.getRotate())
                .radius(((Circle) shape).getRadius())
                .build();
    }
}
