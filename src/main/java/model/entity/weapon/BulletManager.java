package model.entity.weapon;

import model.entity.Asteroid;
import model.entity.Ship;

public interface BulletManager {

    boolean shipBullet(Ship ship);

    void bulletEntity(double damage, Asteroid asteroid);

    void bulletEntity(double damage, Ship ship);
}
