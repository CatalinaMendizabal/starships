package collider;

import model.Entity;
import view.ColliderView;

import java.util.List;
import java.util.stream.Collectors;

public class ColliderManager {

    private final ColliderView visitor = new ColliderView();

    public List<ColliderEntity> generateColliders(List<Entity> entities) {
        return entities.stream().map(gameObject -> {
            gameObject.accept(visitor);
            return visitor.getResult();
        }).collect(Collectors.toList());
    }

}
