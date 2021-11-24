package factory;

import edu.austral.dissis.starships.vector.Vector2;
import model.entity.Asteroid;

import java.util.Random;

public class AsteroidFactory {

    Random random = null;

    public Asteroid createAsteroid(double width, double height) {return createAsteroid((int) (Math.random() * 100), width, height);}

    public Asteroid createAsteroid(int seed, double posX, double posY) {
        if(random == null) random = new Random(seed);
        double health = random.nextInt(300 - 25) + 25.0;
        Random random = new Random();
        if(random.nextBoolean()) {
            posX = random.nextBoolean() ? 0 - health : posX + health;
            posY = random.nextInt((int) posY);
        } else {
            posY = random.nextBoolean() ? 0 - health: posY + health;
            posX = random.nextInt((int) posX);
        }
        return new Asteroid(health, Vector2.vector(posX, posY), random.nextInt(360), 10000 / health);
    }
}
