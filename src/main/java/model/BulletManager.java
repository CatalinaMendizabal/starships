package model;

import model.components.Ship;

public interface BulletManager {
    void addPoints(double points);
    boolean shipBullet(Ship ship);
}
