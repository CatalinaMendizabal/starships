package data;

import edu.austral.dissis.starships.vector.Vector2;
import lombok.Builder;
import lombok.Data;
import model.Ship;
import strategy.Shooting;
import strategy.NormalShooting;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

@Data
@Builder
public class SaveShip implements Serializable {
    private double health;
    private double maxHealth;
    private double posX;
    private double posY;
    private double speed;
    private double angle;
    private Shooting shooting;

    public Ship toShip() {
        Ship ship = Ship.builder()
                .health(health)
                .maxHealth(maxHealth)
                .position(Vector2.vector(posX, posY))
                .direction(angle)
                .speed(speed)
                .shooting(shooting)
                .build();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ship.setShooting(new NormalShooting());
            }
        } , 5000);

        return ship;
    }
}
