/*
package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Player;

public class Bullet2 extends GameObject{

    private final int damage;
    private float speed;
    public final Player shooter;

    public Bullet2( double healthPoints, int damage, float speed, Player shooter) {
        super( healthPoints);
        this.damage = damage;
        this.speed = speed;
        this.shooter = shooter;
        this.shape = new Circle(3);
    }


    public void move(Vector2 to) {
        shape.setLayoutX(to.getX());
        shape.setLayoutY(to.getY());
    }

    @Override
    public void handleCollisionWith(Bullet2 bullet) {}

    @Override
    public void handleCollisionWith(Asteroid2 asteroid) {
        speed = 0;
        if (asteroid.getHealth() < 0) shooter.addPoints(damage);
        shooter.addPoints(damage);
    }

    @Override
    public void handleCollisionWith(Ship2 ship) {}

    @Override
    public Shape getShape() {return shape;}

    @Override
    public void handleCollisionWith(Collisionable collider) {collider.handleCollisionWith(this);}

    public int getDamage() {
        return damage;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public Double getHealth() {
        return healthPoints;
    }

    @Override
    public void setHealth(double v) {
        healthPoints = v;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Player getShooter() {
        return shooter;
    }
}
*/
