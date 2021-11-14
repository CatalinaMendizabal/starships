package model.weapon;

import controller.BulletController;
import javafx.scene.shape.Circle;
import model.Player;
import model.components.Bullet;

public class SingleShooting implements Shooting {

    private double waitABit = 300;
    private double lastShot;

    @Override
    public void shoot(Player shooter, BulletController bulletController, double x, double y, double angle) {
        double currentTime = System.currentTimeMillis();
        if(currentTime - lastShot < waitABit) return;
        lastShot = currentTime;
        Circle shape = new Circle(5);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.setRotate(angle - 90);
        bulletController.addBullet(new Bullet(shape, 300.0, 50, shooter));
    }

    @Override
    public void setWaitABit(double waitABit) {this.waitABit = waitABit;}

    @Override
    public double getWaitABit() {return this.waitABit;}
}
