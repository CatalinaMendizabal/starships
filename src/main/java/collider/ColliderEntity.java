package collider;

import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import model.Entity;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class ColliderEntity implements Collisionable {

    private Entity entity;
    private Shape shape;

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void handleCollisionWith(@NotNull Collisionable collider) {
        entity.handleCollisionWith(collider);
    }

    @Override
    public @NotNull Shape getShape() {return shape;}

}
