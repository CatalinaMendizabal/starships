package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import model.components.data.AsteroidData;

public class Asteroid extends GameObject {

    private final double size;

    public Asteroid(Vector2 position, Vector2 direction, double speed, double health, double size) {
        super(position, direction, speed, health);
        this.size = size;
        this.shape = new Circle(health / 3);
    }

    public void move(Vector2 to) {
        ((Circle) shape).setCenterX(to.getX() + health / 2);
        ((Circle) shape).setCenterY(to.getY() + health / 2);
    }

    @Override
    public void handleCollisionWith(Collisionable collider) {collider.handleCollisionWith(this);}

    @Override
    public void handleCollisionWith(Asteroid asteroid) {}

    @Override
    public void handleCollisionWith(Ship ship) {
        ship.setHealth(ship.getHealth() - health / 2);
        health = 0.0;
    }

    @Override
    public void handleCollisionWith(Bullet bullet) {
        health = health - bullet.getDamage();
    }

    public AsteroidData buildData() {
        return AsteroidData.builder()
                .health(health)
                .position(position)
                .direction(direction)
                .centerX(((Circle) shape).getCenterX())
                .centerY(((Circle) shape).getCenterY())
                .rotate(shape.getRotate())
                .radius(((Circle) shape).getRadius())
                .speed(speed)
                .build();
    }

}
