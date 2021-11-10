package model.entities;

import collider.Collider2;
import controller.BulletController;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Player;
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
        shootingStrategy.shoot(shooter, bulletController, shape.getLayoutX() + ((Rectangle) shape).getWidth()/2 , shape.getLayoutY() + ((Rectangle) shape).getHeight()/2, shape.getRotate());
    }

    @Override
    public void handleCollisionWith(Collider2 collider) {
        collider.handleCollisionWith(this);
    }

    public void move(Vector2 to) {
        shape.setLayoutX(to.getX() + (100 - ((Rectangle) shape).getWidth())/2);
        shape.setLayoutY(to.getY() + (100 - ((Rectangle) shape).getHeight())/2);
    }

    public void heal(int amount) {
        health += amount;
    }


    @Override
    public void handleCollisionWith(Asteroid asteroid) {

    }

    @Override
    public void handleCollisionWith(Ship ship) {

    }

    @Override
    public void handleCollisionWith(Bullet bullet) {

    }


   /* public ShipDTO toDTO() {
        return ShipDTO.builder()
                .health(health)
                .shootingStrategy(shootingStrategy)
                .speed(speed)
                .posX(shape.getLayoutX())
                .posY(shape.getLayoutY())
                .angle(shape.getRotate())
                .build();
    }*/
}
