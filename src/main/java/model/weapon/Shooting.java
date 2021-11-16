package model.weapon;

import controller.BulletController;
import model.Player;

import java.io.Serializable;

// using strategy pattern
public interface Shooting extends Serializable {

    void setWaitABit(double waitABit);

    double getWaitABit();

    void shoot(Player shooter, BulletController bulletController, double x, double y, double angle);

}

