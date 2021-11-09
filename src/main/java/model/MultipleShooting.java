package model;

import controller.BulletController;
import javafx.scene.shape.Circle;

public class MultipleShooting implements Shooting {
    @Override
    public void shoot(BulletController bulletController, double x, double y, double angle) {
        Circle shape = new Circle(5);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.setRotate(angle - 90);
        bulletController.addBullet(new Bullet(shape, 300.0, 50));
    }
}
