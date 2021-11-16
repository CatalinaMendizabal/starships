package utils;

import controller.BulletController;
import controller.ShipController;
import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import model.components.Ship;
import model.weapon.SingleShooting;
import view.ShipView;
import java.io.IOException;

public class Config {
    private static final ImageLoader imageLoader = new ImageLoader();

    public static final int PLAYERS = 2;
    public static final int LIVES = 3;

    public static ShipController[] getGameConfig() {
        try {
            return new ShipController[]{new ShipController(new ShipView(imageLoader.loadFromResources("starship.png", 100, 100), 200, 200), new Ship(200.0, new SingleShooting(), new Rectangle(70, 45), 100), new BulletController()),
                    new ShipController(new ShipView(imageLoader.loadFromResources("starship.png", 100, 100), 1000, 200), new Ship(200.0, new SingleShooting(), new Rectangle(70, 45), 100), new BulletController())
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final KeyCode[][] KEY_CODES = {
            {KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.SPACE, KeyCode.Q},
            {KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT, KeyCode.SHIFT, KeyCode.ENTER}
    };

}
