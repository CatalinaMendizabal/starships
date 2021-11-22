/*
package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.Data;

public class Asteroid2 extends GameObject{

    private final float speed;
    private final int width, height, damage, points;

    public Asteroid2( double healthPoints, float speed, int width, int height, int damage, int points) {
        super( healthPoints);
        this.speed = speed;
        this.damage = damage;
        this.width = width;
        this.height = height;
        this.points = points;
        this.shape = new Circle(healthPoints / 3);
    }

    public void move(Vector2 to) {
        ((Circle) shape).setCenterX(to.getX() + healthPoints / 2);
        ((Circle) shape).setCenterY(to.getY() + healthPoints / 2);
    }


    @Override
    public void handleCollisionWith(Bullet2 bullet) {healthPoints = healthPoints - bullet.getDamage();}

    @Override
    public void handleCollisionWith(Asteroid2 asteroid) {}

    @Override
    public void handleCollisionWith(Ship2 ship) {
        ship.setHealth(ship.getHealth() - healthPoints / 2);
        healthPoints = 0.0;
    }

    @Override
    public Shape getShape() {return shape;}

    public void handleCollisionWith(Collisionable collider) {collider.handleCollisionWith(this);}

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDamage() {
        return damage;
    }

    public int getPoints() {
        return points;
    }
}
*/
