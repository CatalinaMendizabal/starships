package model.weapon;

import controller.BulletController;
import model.Player;

import java.io.Serializable;

public interface Shooting extends Serializable {

    void shoot(Player shooter, BulletController bulletController, double x, double y, double angle);

    void setWaitABit(double waitABit);

    double getWaitABit();
}

