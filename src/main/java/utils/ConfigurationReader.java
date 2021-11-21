package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

import controller.BulletController;
import controller.ShipController;
import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Player;
import model.components.Ship;
import model.weapon.SingleShooting;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.ShipView;

public class ConfigurationReader {

    private static final String FILENAME = "/Users/catamendizabal/projects/dis-sis/starships/starships.json";
    Player[] players = new Player[2];
    JSONParser parser = new JSONParser();
    List<JSONObject> list = new ArrayList<>();
    private static final ImageLoader imageLoader = new ImageLoader();

    public ConfigurationReader() {
        try  {
            // read starships.json file
            Object object = parser.parse(new FileReader(FILENAME));
            JSONObject jsonObject = (JSONObject) object;

            JSONObject player1 = (JSONObject) jsonObject.get("Player1");
            JSONObject player2 = (JSONObject) jsonObject.get("Player2");

            list.add(player1);
            list.add(player2);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void configKeys(Pane pane) {
        for (int i = 0; i < list.size(); i++) {
            players[i] = new Player(i, 0, list.get(i).get("lives").hashCode(), Objects.requireNonNull(getGameConfig())[i],
                    KeyCode.getKeyCode(list.get(i).get("moveUp").toString()),
                    KeyCode.getKeyCode(list.get(i).get("moveDown").toString()),
                    KeyCode.getKeyCode(list.get(i).get("moveLeft").toString()),
                    KeyCode.getKeyCode(list.get(i).get("moveRight").toString()),
                    KeyCode.getKeyCode(list.get(i).get("fire").toString()),
                    KeyCode.getKeyCode(list.get(i).get("changeFire").toString()),
                    true);

            pane.getChildren().add(players[i].getShipController().getShipView().getImageView());
            pane.getChildren().add(players[i].getShipController().getShipView().getHealthView());
            pane.getChildren().add(players[i].getShipController().getShipView().getScore());
        }
    }

    public void configSaveGameKeys(Player[] players, Pane pane) {
        for (Player player : players) {
            pane.getChildren().add(player.getShipController().getShipView().getImageView());
            pane.getChildren().add(player.getShipController().getShipView().getHealthView());
            pane.getChildren().add(player.getShipController().getShipView().getScore());
            pane.getChildren().addAll(player.getShipController().getBulletController().renderBullets());
        }
    }

    public static ShipController[] getGameConfig() {
        try {
            return new ShipController[]{
                    new ShipController(new ShipView(imageLoader.loadFromResources("starship.png", 100, 100), 200, 200), new Ship(200.0, new SingleShooting(), new Rectangle(70, 45), 100), new BulletController()),
                    new ShipController(new ShipView(imageLoader.loadFromResources("starship.png", 100, 100), 1000, 200), new Ship(200.0, new SingleShooting(), new Rectangle(70, 45), 100), new BulletController())
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Player[] getPlayers() { return players; }

}
