package model.components;

import collider.Collider2;
import controller.BulletController;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Player;
import model.components.data.ShipData;
import model.weapon.Shooting;

@AllArgsConstructor
@Data
@Builder
public class Ship implements Collider2 {
    private Double health;
    private Shooting shootingStrategy;
    private Shape shape;
    private double speed;

    public void fire(BulletController bulletController, Player shooter) {
        shootingStrategy.shoot(shooter, bulletController, shape.getLayoutX() + ((Rectangle) shape).getWidth() / 2, shape.getLayoutY() + ((Rectangle) shape).getHeight() / 2, shape.getRotate());
    }

    @Override
    public void handleCollisionWith(Collider2 collider) {
        collider.handleCollisionWith(this);
    }

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX() + (100 - ((Rectangle) shape).getWidth()) / 2);
        shape.setLayoutY(to.getY() + (100 - ((Rectangle) shape).getHeight()) / 2);
    }

    @Override
    public void handleCollisionWith(Asteroid asteroid) {}

    @Override
    public void handleCollisionWith(Ship ship) {}


    public Double getHealth() {return health;}

    public void setHealth(Double health) {
        this.health = health;
    }

    public void setShootingStrategy(Shooting shootingStrategy) {
        this.shootingStrategy = shootingStrategy;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void handleCollisionWith(Bullet bullet) {
        if (bullet.getShooter().getShipController().getShip() != this) {
            health -= bullet.getDamage() / 10;

            bullet.setSpeed(0);
            if (health < 0) bullet.getShooter().addPoints(bullet.getDamage());
            bullet.getShooter().addPoints(bullet.getDamage() / 10);
        }
    }

    public ShipData toDTO() {
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
