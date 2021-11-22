package utils;

import controller.AsteroidController;
import edu.austral.dissis.starships.file.ImageLoader;
import factory.AsteroidFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.components.Asteroid;

public class AsteroidSpawner {

    public void spawnAsteroid(AsteroidFactory asteroidFactory ,AsteroidController asteroidController, ImageLoader imageLoader, Pane pane){
        if (Math.random() * 100.0 < 5) {
            Asteroid asteroid = asteroidFactory.createAsteroid();
            ImageView imageView = asteroidController.spawnAsteroid(asteroid, imageLoader, pane.getWidth(), pane.getHeight());
            pane.getChildren().add(imageView);
        }
    }
}
