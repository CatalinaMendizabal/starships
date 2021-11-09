package model;

import collider.Collider2;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Asteroid implements Collider2 {
    Double health;
    Shape shape;
    double speed;

    public Asteroid(Double health, Shape shape, double speed) {
        this.health = health;
        this.shape = shape;
        this.speed = speed;
    }

    @Override
    public void handleCollisionWith(Collider2 collider) {
        if (collider.getClass().equals(Ship.class)) {
            Ship ship = (Ship) collider;
            ship.setHealth(ship.getHealth() - health / 2);
            health = 0.0;
        } else if (collider.getClass().equals(Bullet.class)) {
            Bullet bullet = (Bullet) collider;
            health = health - bullet.getDamage();
        }
    }

    public void move(Vector2 to) {
        ((Circle) shape).setCenterX(to.getX() + health / 2);
        ((Circle) shape).setCenterY(to.getY() + health / 2);
    }
}
