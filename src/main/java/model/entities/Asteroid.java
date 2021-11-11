package model.entities;

import collider.Collider2;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Asteroid implements Collider2 {

    Double health;
    Shape shape;
    double speed;

    @Override
    public void handleCollisionWith(Collider2 collider) {
        collider.handleCollisionWith(this);
    }

    public void move(Vector2 to) {
        ((Circle) shape).setCenterX(to.getX() + health/2);
        ((Circle) shape).setCenterY(to.getY() + health/2);
    }

    @Override
    public void handleCollisionWith(Asteroid asteroid) {

    }

    @Override
    public void handleCollisionWith(Ship ship) {
        ship.setHealth(ship.getHealth() - health/2);
        health = 0.0;
    }

    @Override
    public void handleCollisionWith(Bullet bullet) {
        health = health - bullet.getDamage();
    }

}
