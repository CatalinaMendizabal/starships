package strategy;

import edu.austral.dissis.starships.vector.Vector2;
import lombok.Getter;
import model.entity.weapon.Bullet;

import java.util.List;

@Getter
public class NormalShooting implements Shooting {

    private double waitABit = 300;
    private double lastShot;

    @Override
    public List<Bullet> shoot(double x, double y, double angle) {
        double currentTime = System.currentTimeMillis();
        if(currentTime - lastShot < waitABit) return List.of();
        lastShot = currentTime;
        return List.of(new Bullet(Vector2.vector(x, y), Math.toRadians(angle - 90), 350.0, 50));
    }

    @Override
    public void setWaitABit(double waitABit) {
        this.waitABit = waitABit;
    }
}
