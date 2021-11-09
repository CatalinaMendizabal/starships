package model;

import collider.Collider2;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.Data;

public class Bullet implements Collider2 {

    Shape shape;
    double speed;
    double damage;

    public Bullet(Circle circle, double speed, double damage) {
        shape = circle;
        this.speed = speed;
        this.damage = damage;
    }

    @Override
    public void handleCollisionWith(Collider2 collider) {
        if (collider instanceof Asteroid) speed = 0;
    }

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX());
        shape.setLayoutY(to.getY());
    }

    public Shape getShape() {return shape;}

    public double getSpeed() {return speed;}

    public double getDamage() {return damage;}
}
