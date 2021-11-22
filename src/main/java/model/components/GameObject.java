package model.components;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Shape;

public abstract class GameObject {

    Double health;
    Shape shape;
    double speed;
    double damage;

    public GameObject(Double health, Shape shape, double speed, double damage) {
        this.health = health;
        this.shape = shape;
        this.speed = speed;
        this.damage = damage;
    }

    public Shape getShape() {return shape;}

    public void setShape(Shape shape) {this.shape = shape;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public double getHealth() {return health;}

    public void setHealth(double health) {this.health = health;}

}
