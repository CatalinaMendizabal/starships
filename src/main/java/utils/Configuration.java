package utils;

import javafx.scene.input.KeyCode;
import lombok.Data;

@Data
public class Configuration {

    public int PLAYERS;
    public int LIVES;
    public KeyCode[][] PLAYER_KEYS;
    public String[] SHIP_NAMES;

    public Configuration() {}
}
