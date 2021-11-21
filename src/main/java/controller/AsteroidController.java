package controller;

import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import lombok.SneakyThrows;
import model.components.Asteroid;
import view.AsteroidView;

import java.util.*;
import java.util.stream.Collectors;

public class AsteroidController {
    List<Asteroid> asteroids = new ArrayList<>();
    List<AsteroidView> asteroidViews = new ArrayList<>();

    public AsteroidController() {}

    @SneakyThrows
    public AsteroidController(List<Asteroid> asteroids) {
        ImageLoader imageLoader = new ImageLoader();
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid = asteroids.get(i);
            Image image = imageLoader.loadFromResources("asteroid.png", asteroid.getHealth(), asteroid.getHealth());
            AsteroidView asteroidView = new AsteroidView(image, ((Circle) asteroid.getShape()).getCenterX() - asteroid.getHealth() / 2, ((Circle) asteroid.getShape()).getCenterY() - asteroid.getHealth() / 2);
            asteroidView.getImageView().setRotate(asteroid.getShape().getRotate());
            asteroidViews.add(i, asteroidView);
        }
        this.asteroids = asteroids;
    }

    public List<Asteroid> getAsteroids() {return asteroids;}

    public List<ImageView> getViews() {return asteroidViews.stream().map(AsteroidView::getImageView).collect(Collectors.toList());}

    @SneakyThrows
    public ImageView spawnAsteroid(Asteroid asteroid, ImageLoader imageLoader, double height, double width) {
        Random random = new Random();
        Image image = imageLoader.loadFromResources("asteroid.png", asteroid.getHealth(), asteroid.getHealth());

        if (random.nextBoolean()) {
            height = random.nextBoolean() ? 0 - image.getWidth() : height + image.getWidth();
            width = random.nextInt((int) width);
        } else {
            width = random.nextBoolean() ? 0 - image.getHeight() : width + image.getHeight();
            height = random.nextInt((int) height);
        }
        asteroids.add(asteroid);
        AsteroidView asteroidView = new AsteroidView(image, height, width);
        asteroidViews.add(asteroidView);
        asteroid.getShape().setRotate(asteroidView.getRotate());

        return asteroidView.getImageView();
    }

    public void removeAsteroids(double width, double height) {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            AsteroidView v = asteroidViews.get(i);
            if (v.getLayoutX() < 0 - a.getHealth() * 2 || v.getLayoutX() > width + a.getHealth() * 2 || v.getLayoutY() < 0 - a.getHealth() * 2 || v.getLayoutY() > height + a.getHealth() * 2) {
                a.setHealth(0.0);
            }
        }
    }

    public List<ImageView> deathControl() {
        List<ImageView> deaths = new ArrayList<>();
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            AsteroidView v = asteroidViews.get(i);
            if (a.getHealth() <= 0) {
                deaths.add(v.getImageView());
                asteroids.remove(a);
                asteroidViews.remove(v);
            }
        }
        return deaths;
    }

    public void updatePos(Double lastFrame) {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            AsteroidView v = asteroidViews.get(i);
            Vector2 movementVector = Vector2.vectorFromModule((a.getSpeed() * lastFrame), (Math.toRadians(v.getRotate())));
            Vector2 from = Vector2.vector(v.getLayoutX(), v.getLayoutY());
            Vector2 to = from.add(movementVector);
            v.getImageView().setFitWidth(a.getHealth());
            v.getImageView().setFitHeight(a.getHealth());
            ((Circle) (a.getShape())).setRadius(a.getHealth() / 3);
            v.move(to);
            a.move(to);
        }
    }
}
