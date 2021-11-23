package strategy;

import model.entity.weapon.Bullet;

import java.io.Serializable;
import java.util.List;

public interface Shooting extends Serializable {

    List<Bullet> shoot(double x, double y, double angle);

    void setWaitABit(double waitABit);

    double getWaitABit();
}
