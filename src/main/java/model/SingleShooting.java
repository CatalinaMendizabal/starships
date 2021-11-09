package model;

import controller.BulletController;
import javafx.scene.shape.Circle;

public class SingleShooting implements Shooting {

    private double waitABit = 100;
    private double lastShot;

    public void checkShooting(double time, Circle bullet, BulletController bulletController) {
        if (time - lastShot > waitABit) {
            lastShot = time;
        }
    }

    @Override
    public void shoot(BulletController bulletController, double x, double y, double angle) {
        double currentTime = System.currentTimeMillis();
        if (currentTime - lastShot < waitABit) return;
        lastShot = currentTime;

        Circle shape = new Circle(5);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.setRotate(angle - 90);
        bulletController.addBullet(new Bullet(shape, 300.0, 50));
    }
}
