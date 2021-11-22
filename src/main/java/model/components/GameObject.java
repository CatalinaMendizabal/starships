package model.components;
import collider.Collisionable;
import javafx.scene.shape.Shape;

public abstract class GameObject implements Collisionable {

    Double health;
    Shape shape;
    double speed;

    public GameObject(Double health, Shape shape, double speed) {
        this.health = health;
        this.shape = shape;
        this.speed = speed;
    }

    public Shape getShape() {return shape;}

    @Override
    public void handleCollisionWith( Collisionable collisionable) {}

    public void setShape(Shape shape) {this.shape = shape;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public double getHealth() {return health;}

    public void setHealth(double health) {this.health = health;}

    @Override
    public void handleCollisionWith(Bullet bullet) {}

    @Override
    public void handleCollisionWith(Asteroid asteroid) {}

    @Override
    public void handleCollisionWith(Ship ship) {}
}
