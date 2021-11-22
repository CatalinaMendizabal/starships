package model.weapon;

import controller.BulletController;
import javafx.scene.shape.Circle;
import model.BulletManager;
import model.Player;
import model.components.Bullet;

import java.util.List;

public class MultipleShooting implements Shooting {
    private double waitABit = 300;
    private double lastShot;

    private Bullet createBullet(double x, double y, double angle, BulletManager shooter) {
        Circle shape = new Circle(5);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.setRotate(angle - 90);
        return new Bullet(shape, 300.0, 50, shooter);
    }

    @Override
    public List<Bullet> shoot(BulletManager shooter, double x, double y, double angle) {
        double currentTime = System.currentTimeMillis();
        if(currentTime - lastShot < waitABit) return List.of();
        lastShot = currentTime;
        return List.of(
                createBullet(x, y, angle, shooter),
                createBullet(x, y, angle - 10, shooter),
                createBullet(x, y, angle + 10, shooter)
        );
    }

    @Override
    public void setWaitABit(double waitABit) {this.waitABit = waitABit;}

    @Override
    public double getWaitABit() {return waitABit;}

}
