package view;

import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.image.Image;
import lombok.Getter;
import model.Asteroid;
import model.weapon.Bullet;
import model.Ship;
import view.entity.AsteroidView;
import view.entity.BulletView;
import view.entity.ShipView;

import java.io.IOException;

public class ImageView implements EntityView {
    private Image asteroid;
    private Image ship1;
    private Image ship2;
    private Image bullet;

    @Getter
    private View result;

    public ImageView() {
        ImageLoader imageLoader = new ImageLoader();
        try {
            this.asteroid = imageLoader.loadFromResources("asteroid.png", 500, 500);
            this.ship1 = imageLoader.loadFromResources("starship.png", 100, 100);
            this.ship2 = imageLoader.loadFromResources("starships3.png", 100, 100);
            this.bullet = imageLoader.loadFromResources("bullet3.png", 20, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitAsteroid(Asteroid asteroid) {
        result = new AsteroidView(this.asteroid, asteroid.getPosition().getX(), asteroid.getPosition().getY(), asteroid.getDirection());
        result.getImageView().setFitWidth(asteroid.getHealth());
        result.getImageView().setFitHeight(asteroid.getHealth());
    }

    @Override
    public void visitShip(Ship ship) {
        if (ship.getName().equals("starship.png"))
            result = new ShipView(ship1, (int) ship.getPosition().getX(), (int) ship.getPosition().getY());
        else
            result = new ShipView(ship2, (int) ship.getPosition().getX(), (int) ship.getPosition().getY());
    }

    @Override
    public void visitBullet(Bullet bullet) {result = new BulletView(this.bullet, bullet.getPosition().getX(), bullet.getPosition().getY(), bullet.getDirection());}

}
