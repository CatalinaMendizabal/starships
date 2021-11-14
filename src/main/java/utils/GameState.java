package utils;

import lombok.Data;
import model.AsteroidDTO;
import model.Player;
import model.PlayerDTO;
import model.entities.Asteroid;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameState implements Serializable {

    private final List<PlayerDTO> players;
    private final List<AsteroidDTO> asteroids;

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
