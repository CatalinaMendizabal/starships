package view;

import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.layout.Pane;
import model.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageRenderer {
    private final List<Entity> entities = new ArrayList<>();
    private final List<View> views = new ArrayList<>();
    private final ImageView visitor = new ImageView();
    private final Pane pane;

    public ImageRenderer(Pane pane) {
        this.pane = pane;
    }

    public void renderObjects(List<Entity> toRender) {
        pane.getChildren().removeAll(views.stream().map(View::getImageView).collect(Collectors.toList()));
        views.clear();
        entities.clear();
        toRender.forEach(gameObject -> {
            if (!entities.contains(gameObject)) {
                gameObject.accept(visitor);
                View result = visitor.getResult();
                entities.add(gameObject);
                views.add(result);
                pane.getChildren().add(result.getImageView());
            }
            if(gameObject.shouldBeRemoved()) {
                pane.getChildren().remove(views.get(entities.indexOf(gameObject)).getImageView());
                views.remove(views.get(entities.indexOf(gameObject)));
                entities.remove(gameObject);
                return;
            }
            render(gameObject);
        });
    }

    private void render(Entity entity) {
        View view = views.get(entities.indexOf(entity));
        Vector2 position = entity.getPosition();
        view.getImageView().setLayoutX(position.getX());
        view.getImageView().setLayoutY(position.getY());
        view.getImageView().setRotate(entity.getDirection());
    }
}
