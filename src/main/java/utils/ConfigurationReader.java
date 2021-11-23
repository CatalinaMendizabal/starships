package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.BulletController;
import controller.ShipController;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.input.KeyCode;
import lombok.SneakyThrows;
import model.entity.Ship;
import strategy.NormalShooting;
import view.entity.ShipView;

import java.io.File;
import java.io.IOException;

public class ConfigurationReader {
    private static final ImageLoader imageLoader = new ImageLoader();

    public static int PLAYERS;
    public static int LIVES;

    public static KeyCode[][] PLAYER_KEYS;

    public static void setShip(int i, String shipName) {SHIP_NAMES[i] = shipName;}

    public static final String[] SHIP_NAMES = {"starship.png", "starship.png"};

    public static ShipController[] getPlayerShips() {
        try {
            return new ShipController[]{
                    new ShipController(new ShipView(imageLoader.loadFromResources(SHIP_NAMES[0], 100, 100), 200, 200), new Ship(200.0, new NormalShooting(), Vector2.vector(200, 200), 200), new BulletController()),
                    new ShipController(new ShipView(imageLoader.loadFromResources(SHIP_NAMES[1], 100, 100), 1000, 200), new Ship(200.0, new NormalShooting(), Vector2.vector(1000, 200), 200), new BulletController())
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void reloadConfig() {
        ObjectMapper mapper = new ObjectMapper();
        Configuration configuration = mapper.readValue(new File("starships.json"), Configuration.class);
        PLAYERS = configuration.PLAYERS;
        LIVES = configuration.LIVES;
        PLAYER_KEYS = configuration.PLAYER_KEYS;
    }
}
