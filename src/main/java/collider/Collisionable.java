package collider;

import edu.austral.dissis.starships.collision.Collider;
import model.entity.Entity;

public interface Collisionable extends Collider<Collisionable> {

    Entity getEntity();

}
