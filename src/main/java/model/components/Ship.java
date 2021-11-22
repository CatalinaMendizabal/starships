package model.components;

import collider.Collisionable;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Player;
import model.components.data.ShipData;
import model.weapon.Shooting;
import model.weapon.SingleShooting;

import java.util.List;


public class Ship extends GameObject implements Collisionable {

    private Shooting shootingStrategy;

    public Ship(Double health, Shape shape, double speed) {
        super(health, shape, speed, 0);
        this.shootingStrategy = new SingleShooting();
    }

    public List<Bullet> fire(Player shooter) {
        return shootingStrategy.shoot(shooter, shape.getLayoutX() + ((Rectangle) shape).getWidth() / 2, shape.getLayoutY() + ((Rectangle) shape).getHeight() / 2, shape.getRotate());
        //return shootingStrategy.shoot(position.getX() + 50 , position.getY() + 50, 0);
    }

    @Override
    public void handleCollisionWith(Collisionable collider) {collider.handleCollisionWith(this);}

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX() + (100 - ((Rectangle) shape).getWidth()) / 2);
        shape.setLayoutY(to.getY() + (100 - ((Rectangle) shape).getHeight()) / 2);
    }

    @Override
    public void handleCollisionWith(Bullet bullet) {
        if (bullet.getBulletManager() == null || !bullet.getBulletManager().shipBullet(this)) {
            health -= bullet.getDamage() / 10;
            bullet.setSpeed(0);
            if (bullet.getBulletManager() != null) {
                if (health < 0) bullet.getBulletManager().addPoints(bullet.getDamage());
                bullet.getBulletManager().addPoints(bullet.getDamage() / 10);
            }
        }
    }

    @Override
    public void handleCollisionWith(Asteroid asteroid) {
        health -= asteroid.getHealth() / 2;
        asteroid.setHealth(0.0);
    }

    @Override
    public void handleCollisionWith(Ship ship) {}

    public double getHealth() {return health;}

    public void setHealth(Double health) {
        this.health = health;
    }

    public void setShootingStrategy(Shooting shootingStrategy) {
        this.shootingStrategy = shootingStrategy;
    }

    public void setShape(Shape shape) {this.shape = shape;}


    public ShipData buildData() {
        return ShipData.builder()
                .health(health)
                .shootingStrategy(shootingStrategy)
                .speed(speed)
                .posX(shape.getLayoutX())
                .posY(shape.getLayoutY())
                .angle(shape.getRotate())
                .build();
    }
}
