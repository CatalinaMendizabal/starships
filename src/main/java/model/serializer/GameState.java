package model.serializer;

import lombok.Data;
import model.components.data.AsteroidData;
import model.Player;
import model.components.data.PlayerData;
import model.components.Asteroid;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameState implements Serializable {

    private final List<PlayerData> players;
    private final List<AsteroidData> asteroids;

    public GameState(Player[] players, List<Asteroid> asteroids) {
        this.players = new ArrayList<>();
        for (Player player : players) {
            this.players.add(player.buildData());
        }
        this.asteroids = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            this.asteroids.add(asteroid.buildData());
        }
    }

}
