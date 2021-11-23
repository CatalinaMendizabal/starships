package serializer;

import data.SaveAsteroid;
import data.SavePlayer;
import lombok.Data;
import model.Asteroid;
import player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameState implements Serializable {

    private final List<SavePlayer> players;
    private final List<SaveAsteroid> asteroids;

    public GameState(Player[] players, List<Asteroid> asteroids) {
        this.players = new ArrayList<>();
        for (Player player : players) {
            this.players.add(player.toDTO());
        }
        this.asteroids = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            this.asteroids.add(asteroid.toDTO());
        }
    }
}
