package model.weapon;

import controller.BulletController;
import javafx.scene.shape.Circle;
import model.Player;
import model.components.Bullet;

public class MultipleShooting implements Shooting {
    private double waitABit = 300;
    private double lastShot;

    @Override
    public void shoot(Player shooter, BulletController bulletController, double x, double y, double angle) {
        double currentTime = System.currentTimeMillis();
        if(currentTime - lastShot < waitABit) return;
        lastShot = currentTime;
        bulletController.addBullet(createBullet(x, y, angle, shooter));
        bulletController.addBullet(createBullet(x, y, angle - 15, shooter));
        bulletController.addBullet(createBullet(x, y, angle + 15, shooter));
    }

    @Override
    public void setWaitABit(double waitABit) {this.waitABit = waitABit;}

    @Override
    public double getWaitABit() {return waitABit;}

    private Bullet createBullet(double x, double y, double angle, Player shooter) {
        Circle shape = new Circle(5);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.setRotate(angle - 90);
        return new Bullet(shape, 300.0, 50, shooter);
    }
}
