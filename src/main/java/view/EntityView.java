package view;

import model.entity.Asteroid;
import model.entity.weapon.Bullet;
import model.entity.Ship;

public interface EntityView {

    void visitAsteroid(Asteroid asteroid);

    void visitShip(Ship ship);

    void visitBullet(Bullet bullet);
}
