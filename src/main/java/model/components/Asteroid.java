package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.components.data.AsteroidData;

public class Asteroid extends GameObject implements Collisionable {

    public Asteroid(Double health, Shape shape, double speed) {super(health, shape, speed);}

    public Vector2 getPosition() {return Vector2.vector(((Circle) shape).getCenterX(), ((Circle) shape).getCenterY());}

    public double getDirection() {return shape.getRotate();}

    public double getSpeed() {return speed;}

    public void move(Vector2 to) {
        ((Circle) shape).setCenterX(to.getX() + health / 2);
        ((Circle) shape).setCenterY(to.getY() + health / 2);
    }

    public double getHealth() {return health;}

    public void setHealth(Double health) {this.health = health;}


    @Override
    public Shape getShape() {return shape;}

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
    public void handleCollisionWith(Bullet bullet) {health = health - bullet.getDamage();}

    public AsteroidData buildData() {
        return AsteroidData.builder()
                .health(health)
                .centerX(((Circle) shape).getCenterX())
                .centerY(((Circle) shape).getCenterY())
                .rotate(shape.getRotate())
                .radius(((Circle) shape).getRadius())
                .speed(speed)
                .build();
    }

}
