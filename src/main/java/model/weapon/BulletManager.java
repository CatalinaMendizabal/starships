package model.weapon;

import model.Asteroid;
import model.Ship;

public interface BulletManager {

    boolean shipBullet(Ship ship);

    void bulletEntity(double damage, Asteroid asteroid);

    void bulletEntity(double damage, Ship ship);
}
