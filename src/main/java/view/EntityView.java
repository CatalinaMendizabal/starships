package view;

import model.Asteroid;
import model.weapon.Bullet;
import model.Ship;

public interface EntityView {

    void visitAsteroid(Asteroid asteroid);

    void visitShip(Ship ship);

    void visitBullet(Bullet bullet);
}
