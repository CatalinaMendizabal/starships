package strategy;

import edu.austral.dissis.starships.vector.Vector2;
import model.weapon.Bullet;

import java.util.List;

public class MultipleShooting implements Shooting {

    private double waitABit = 300;
    private double lastShot;

    @Override
    public List<Bullet> shoot(double x, double y, double angle) {
        double currentTime = System.currentTimeMillis();
        if(currentTime - lastShot < waitABit) return List.of();
        lastShot = currentTime;
//        bulletController.addBullet(createBullet(x, y, angle, shooter));
//        bulletController.addBullet(createBullet(x, y, angle - 15, shooter));
//        bulletController.addBullet(createBullet(x, y, angle + 15, shooter));
        return List.of(
                createBullet(x, y, angle),
                createBullet(x, y, angle - 30),
                createBullet(x, y, angle + 30)
        );
    }

    @Override
    public void setWaitABit(double waitABit) {
        this.waitABit = waitABit;
    }

    @Override
    public double getWaitABit() {
        return waitABit;
    }

    private Bullet createBullet(double x, double y, double angle) {
        return new Bullet(Vector2.vector(x, y), Math.toRadians(angle - 90), 300.0, 50);
    }
}
