package model.weapon;

import controller.BulletController;
import model.Player;

public interface Shooting {

    void shoot(Player shooter, BulletController bulletController, double x, double y, double angle);

    void setWaitABit(double waitABit);

    double getWaitABit();
}

