package view;

import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.Data;

public class AsteroidView {
    ImageView imageView;

    public AsteroidView(Image image, double posX, double posY) {
        imageView = new ImageView(image);
        imageView.setLayoutX(posX);
        imageView.setLayoutY(posY);
        imageView.setRotate(Math.random() * 359);
    }

    public double getRotate() {
        return imageView.getRotate();
    }

    public void move(Vector2 to) {
        imageView.setLayoutX(to.getX());
        imageView.setLayoutY(to.getY());
    }

    public ImageView getImageView() {return imageView;}

    public double getLayoutX() {
        return imageView.getLayoutX();
    }

    public double getLayoutY() {
        return imageView.getLayoutY();
    }
}
