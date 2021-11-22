package model.components;
import javafx.scene.shape.Shape;

public class GameObject {

    Double health;
    Shape shape;
    double speed;

    public GameObject(Double health, Shape shape, double speed) {
        this.health = health;
        this.shape = shape;
        this.speed = speed;
    }

    public Shape getShape() {return shape;}

    public void setShape(Shape shape) {this.shape = shape;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public double getHealth() {return health;}

    public void setHealth(double health) {this.health = health;}
}

/*

package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Shape;
import lombok.Builder;
import lombok.Data;


@Data
public abstract class GameObject implements Collisionable {

*/
/*    public Vector2 position;
    public Vector2 direction;*//*


    public Vector2 position;
    public double direction;
    public Shape shape;
    public double speed;
    public double health;
    float directionAngle;

    public GameObject( double speed, double health) {
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
*/
