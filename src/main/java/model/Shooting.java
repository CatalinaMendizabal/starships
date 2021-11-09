package model;

import controller.BulletController;

public interface Shooting {
    void shoot(BulletController bulletController, double x, double y, double angle);
}
