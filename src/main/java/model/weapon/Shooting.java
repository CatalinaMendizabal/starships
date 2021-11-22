package model.weapon;

import controller.BulletController;
import model.BulletManager;
import model.Player;
import model.components.Bullet;

import java.io.Serializable;
import java.util.List;

// using strategy pattern
public interface Shooting extends Serializable {

    void setWaitABit(double waitABit);

    double getWaitABit();

    List<Bullet> shoot(BulletManager shooter, double x, double y, double angle);

}

