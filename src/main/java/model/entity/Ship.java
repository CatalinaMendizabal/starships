package model.entity;

import collider.Collisionable;
import collider.EntityCollision;
import data.SaveShip;
import edu.austral.dissis.starships.vector.Vector2;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.entity.weapon.Bullet;
import model.entity.weapon.BulletManager;
import strategy.Shooting;
import view.EntityView;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Ship implements Entity, EntityCollision {
    private Double health;
    private Double maxHealth;
    private Shooting shooting;

    private Vector2 position;
    private double direction;
    private double speed;

    @Builder.Default
    private String name = "starship.png";

    public Ship(Double health, Shooting shooting, Vector2 position, double speed) {
        this.health = health;
        this.maxHealth = health;
        this.shooting = shooting;
        this.position = position;
        direction = 0;
        this.speed = speed;
    }

    public List<Bullet> fire() {
        return shooting.shoot(position.getX() + 50 , position.getY() + 50, direction);
    }

    public void handleCollisionWith(Collisionable collider) {
        collider.getEntity().handleCollisionWith(this);
    }

    public void move(Vector2 to) {
        position = to;
    }

    public void heal(int amount) {
        health += amount;
        if(health > maxHealth) health = maxHealth;
    }

    public void handleCollisionWith(Bullet bullet) {
        if (bullet.getObservers() == null || !bullet.getObservers().get(0).shipBullet(this)) {
            health -= bullet.getDamage() / 10;

            bullet.setSpeed(0);
            if(bullet.getObservers() != null) {
                for (BulletManager observer : bullet.getObservers()) {
                    observer.bulletEntity(bullet.getDamage(), this);
                }
            }
        }
    }

    @Override
    public void handleCollisionWith(Asteroid asteroid) {
        health -= asteroid.getHealth()/2;
        asteroid.setHealth(0.0);
    }

    @Override
    public void handleCollisionWith(Ship ship) {}

    public SaveShip toDTO() {
        return SaveShip.builder()
                .health(health)
                .maxHealth(maxHealth)
                .shooting(shooting)
                .speed(speed)
                .posX(position.getX())
                .posY(position.getY())
                .angle(direction)
                .build();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public boolean shouldBeRemoved() {
        return health <= 0;
    }

    @Override
    public void accept(EntityView visitor) {
        visitor.visitShip(this);
    }

}
