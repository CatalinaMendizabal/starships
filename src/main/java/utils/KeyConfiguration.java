package utils;

import javafx.scene.layout.Pane;
import model.Player;

import java.util.Objects;

public class KeyConfiguration {

    public void configKeys(Player[] players, Pane pane) {
        for (int i = 0; i < Config.PLAYERS; i++) {
            players[i] = new Player(i, 0, Config.LIVES, Objects.requireNonNull(Config.getPlayerShips())[i],
                    Config.PLAYER_KEYS[i][0],
                    Config.PLAYER_KEYS[i][1],
                    Config.PLAYER_KEYS[i][2],
                    Config.PLAYER_KEYS[i][3],
                    Config.PLAYER_KEYS[i][4],
                    Config.PLAYER_KEYS[i][5], true);

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
}
