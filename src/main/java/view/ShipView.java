package view;

import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.Data;

@Data
public class ShipView {
    ImageView imageView;
    Group healthView;
    Rectangle healthFill = new Rectangle(500, 20);
    Text score = new Text();


    public ShipView(Image image, int x, int y) {
        this.imageView = new ImageView(image);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        healthFill.setFill(Color.web("#5A2363"));
        healthFill.setArcHeight(20);
        healthFill.setArcWidth(20);


        Rectangle rectangle = new Rectangle(500, 20);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLUEVIOLET);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        rectangle.setStrokeWidth(2);

        healthView = new Group(rectangle, healthFill);
        healthView.setLayoutX(x - 180);
        healthView.setLayoutY(15);

        score.setLayoutX(x + 350);
        score.setLayoutY(40);
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        score.setFill(Color.WHITE);
        score.setText("0");
    }

    public void updateHealth(Double health) {healthFill.setWidth(health * 2.5);}

    public void updatePoints(int points) {this.score.setText(String.valueOf(points));}

    public double getLayoutX() {
        return imageView.getLayoutX();
    }

    public double getLayoutY() {
        return imageView.getLayoutY();
    }

    public double getRotate() {
        return imageView.getRotate();
    }

    public void setRotate(double v) {
        imageView.setRotate(v);
    }

    public void move(Vector2 to) {
        imageView.setLayoutX(to.getX());
        imageView.setLayoutY(to.getY());
    }

}
