package view.entity;

import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.Data;
import lombok.SneakyThrows;
import view.View;

@Data
public class ShipView implements View {
    ImageView imageView;
    Group healthView;
    Rectangle healthFill = new Rectangle(600, 35);
    Text points = new Text();

    public ShipView(Image image, int x, int y) {
        this.imageView = new ImageView(image);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        healthFill.setFill(Color.web("#5A2363"));
        healthFill.setArcHeight(20);
        healthFill.setArcWidth(20);


        Rectangle rectangle = new Rectangle(600, 35);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLUEVIOLET);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        rectangle.setStrokeWidth(2);

        healthView = new Group(rectangle, healthFill);
        healthView.setLayoutX(x);
        healthView.setLayoutY(y);

        points.setLayoutX(x);
        points.setLayoutY(y);
        points.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        points.setFill(Color.WHITE);
        points.setText("0");
    }


    public void updateHealth(Double health) {healthFill.setWidth(health * 3);}

    public void updatePoints(int points) {
        this.points.setText(String.valueOf(points));
    }

    public double getWidth() {
        return imageView.getImage().getWidth();
    }

    public double getHeight() {
        return imageView.getImage().getHeight();
    }

    @SneakyThrows
    public void updateShipStyle(String name) {
        ImageLoader imageLoader = new ImageLoader();
        imageView.setImage(imageLoader.loadFromResources(name, 100, 100));
    }
}
