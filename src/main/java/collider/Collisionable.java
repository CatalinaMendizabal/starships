package collider;

import edu.austral.dissis.starships.collision.Collider;
import model.Entity;

public interface Collisionable extends Collider<Collisionable> {

    Entity getEntity();

}
