package model.components;

import collider.Collider2;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.Data;
import model.components.data.BulletDTO;
import model.Player;

@Data
public class Bullet implements Collider2 {

    Shape shape;
    double speed;
    double damage;
    Player shooter;

    public Bullet(Circle circle, double speed, double damage, Player shooter) {
        shape = circle;
        this.speed = speed;
        this.damage = damage;
        this.shooter = shooter;
    }

    @Override
    public void handleCollisionWith(Collider2 collider) {
        // double dispatch
        collider.handleCollisionWith(this);
    }

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX());
        shape.setLayoutY(to.getY());
    }

    @Override
    public void handleCollisionWith(Asteroid asteroid) {
        speed = 0;
        if (asteroid.getHealth() < 0) shooter.addPoints(damage);
        shooter.addPoints(damage);
    }

    @Override
    public void handleCollisionWith(Ship ship) {}

    @Override
    public void handleCollisionWith(Bullet bullet) {}


    public BulletDTO toDTO() {
        return BulletDTO.builder()
                .speed(speed)
                .damage(damage)
                .posX(shape.getLayoutX())
                .posY(shape.getLayoutY())
                .rotate(shape.getRotate())
                .radius(((Circle) shape).getRadius())
                .build();
    }
}