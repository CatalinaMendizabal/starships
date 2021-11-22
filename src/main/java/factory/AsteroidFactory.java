package factory;

import edu.austral.dissis.starships.vector.Vector2;
import model.components.Asteroid;

import java.util.Random;

public class AsteroidFactory {

    public Asteroid createAsteroid() {return createAsteroid((int) (Math.random() * 100));}

    public Asteroid createAsteroid(int seed) {
        Random random = new Random(seed);
        double health = random.nextInt(300 - 25) + 25.0;
        return new Asteroid(Vector2.vector(9,10), Vector2.vector(9,10), 1/health * 10000,  health, health);
    }
}
