package model;

import collider.Collider2;
import controller.BulletController;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Ship implements Collider2 {
    private Double health;
    private Shooting shooting;
    private Shape shape;

    public Ship(Double health, Shooting shooting, Shape shape) {
        this.health = health;
        this.shooting = shooting;
        this.shape = shape;
    }

    public void fire(BulletController bulletController) {
        shooting.shoot(bulletController, shape.getLayoutX() + ((Rectangle) shape).getWidth()/2 , shape.getLayoutY() + ((Rectangle) shape).getHeight()/2, shape.getRotate());
    }

    @Override
    public void handleCollisionWith(Collider2 collider) {

    }

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX() + (100 - ((Rectangle) shape).getWidth())/2);
        shape.setLayoutY(to.getY() + (100 - ((Rectangle) shape).getHeight())/2);
    }

    public Double getHealth() {return health;}

    public Shooting getShooting() {return shooting;}

    public Shape getShape() {return shape;}

    public void setHealth(Double health) {this.health = health;}
}


