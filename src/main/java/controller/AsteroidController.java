package controller;

import model.entity.Asteroid;

import java.util.*;

public class AsteroidController {
    private List<Asteroid> asteroids = new ArrayList<>();

    public AsteroidController() {}

    public AsteroidController(List<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public void spawnAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void updateDeaths() {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            if (a.getHealth() <= 0) {
                asteroids.remove(a);
            }
        }
    }

    public void killOutOfBounds(double width, double height) {
        for (Asteroid a : asteroids) {
            if (a.getPosition().getX() < 0 - a.getHealth() * 2 || a.getPosition().getX() > width + a.getHealth() * 2 || a.getPosition().getY() < 0 - a.getHealth() * 2 || a.getPosition().getY() > height + a.getHealth() * 2) {
                a.setHealth(0.0);
            }
        }
    }
}
