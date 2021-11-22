
package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Shape;
import lombok.Builder;
import lombok.Data;


@Data
public abstract class GameObject implements Collisionable {

    public Vector2 position;
    public Vector2 direction;
    public Shape shape;
    public double speed;
    public double health;
    float directionAngle;

    public GameObject(Vector2 position, Vector2 direction, double speed, double health) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.health = health;
    }

    public abstract void handleCollisionWith(Ship starship);

    public abstract void handleCollisionWith(Bullet bullet);

    public abstract void handleCollisionWith(Asteroid asteroid);

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public double getHealth() {return health;}

    public void setHealth(double health) {this.health = health;}

    public float getDirectionAngle() {return directionAngle;}

}
