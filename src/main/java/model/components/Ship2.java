/*
package model.components;

import collider.Collisionable;
import controller.BulletController;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.Data;
import model.Player;
import model.weapon.Shooting;
import model.weapon.SingleShooting;

public class Ship2 extends GameObject{

    Shooting shootingStrategy;

    public Ship2( double healthPoints) {
        super( healthPoints);
        this.shape = new Rectangle(70, 45);
        this.speed = 250;
        this.shootingStrategy = new SingleShooting();
    }

    public void fire(BulletController bulletController, Player shooter) {
        shootingStrategy.shoot(shooter, bulletController, shape.getLayoutX() + ((Rectangle) shape).getWidth() / 2, shape.getLayoutY() + ((Rectangle) shape).getHeight() / 2, shape.getRotate());
    }

    public void setShootingStrategy(Shooting shootingStrategy) {
        this.shootingStrategy = shootingStrategy;
    }

    @Override
    public void handleCollisionWith(Collisionable collider) {collider.handleCollisionWith(this);}

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX());
        shape.setLayoutY(to.getY());
    }

    @Override
    public void handleCollisionWith(Bullet2 bullet) {
         if (bullet.getShooter().getShipController().getShip() != this) {
            healthPoints -= bullet.getDamage() / 10;

            bullet.setSpeed(0);
            if (healthPoints < 0) bullet.getShooter().addPoints(bullet.getDamage());
            bullet.getShooter().addPoints(bullet.getDamage() / 10);
        }
    }

    @Override
    public void handleCollisionWith(Asteroid2 asteroid) {}

    @Override
    public void handleCollisionWith(Ship2 ship) {}

    @Override
    public Double getHealth() {
        return healthPoints;
    }

    @Override
    public void setHealth(double v) {
        healthPoints = v;
    }

    @Override
    public Shape getShape() {return shape;}

    public void setShape(Shape shape) {this.shape = shape;}
}
*/
