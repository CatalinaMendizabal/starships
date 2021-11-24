package view;

import collider.ColliderEntity;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.entity.Asteroid;
import model.entity.weapon.Bullet;
import model.entity.Ship;

public class ColliderView implements EntityView {

    ColliderEntity result;

    @Override
    public void visitAsteroid(Asteroid asteroid) {
        Shape shape = new Rectangle(asteroid.getHealth(), asteroid.getHealth());
        setPosition(shape, asteroid.getPosition().getX(), asteroid.getPosition().getY(), asteroid.getDirection());
        result = new ColliderEntity(asteroid, shape);
    }

    @Override
    public void visitShip(Ship ship) {
        Shape shape = new Rectangle(70, 45);
        setPosition(shape, ship.getPosition().getX(), ship.getPosition().getY(), ship.getDirection());
        result = new ColliderEntity(ship, shape);
    }

    @Override
    public void visitBullet(Bullet bullet) {
        Shape shape = new Circle(5);
        setPosition(shape, bullet.getPosition().getX(), bullet.getPosition().getY(), bullet.getDirection());
        result = new ColliderEntity(bullet, shape);
    }


    private void setPosition(Shape shape, double x, double y, double direction) {
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.setRotate(Math.toDegrees(direction));
    }

    public ColliderEntity getResult() {
        return result;
    }
}
