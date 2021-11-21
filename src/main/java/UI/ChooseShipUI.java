package UI;

import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.RootSetter;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.ConfigurationReader;

import java.io.IOException;

public class ChooseShipUI {
    ImageLoader imageLoader = new ImageLoader();
    Button back = createButtons("Back Home", 150);
    Button next = createButtons("Next", 345);
    Button ship1;
    Button ship2;

    public void generateShipUI(Pane pane, int i, RootSetter rootSetter) throws IOException {
        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        Text title1 = createTitle(i + 1);
        ship1 = createShipSelector(150,   "starship.png" );
        ship2 = createShipSelector(350, "starships3.png");
        ship1.setOnMouseClicked(e -> {ConfigurationReader.setShip(i, "starship.png");});
        ship2.setOnMouseClicked(e -> {ConfigurationReader.setShip(i, "starships3.png");});

        pane.getChildren().addAll(backgroundView, title1, next, back, ship1, ship2);
    }

    private Button createButtons(String title, int posX) {
        Button btn = new Button(title);
        btn.setLayoutX(posX);
        btn.setLayoutY(350);
        btn.setPrefWidth(170);
        btn.setPrefHeight(20);
        btn.setFont(Font.font("Verdana", 18));
        btn.setStyle("-fx-background-color: #5A2363; -fx-text-fill: #ffffff;");
        return btn;
    }

    private Text createTitle(int i) {
        Text text = new Text("Player " + i +", choose your ship");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(20, Color.BLACK));
        text.setX(150);
        text.setY(60);
        return text;
    }

    private Button createShipSelector(int posX, String ship) throws IOException {
        Image img = imageLoader.loadFromResources(ship, 160, 160);;
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        Button button = new Button();
        button.setPrefHeight(200);
        button.setTranslateX(posX);
        button.setTranslateY(120);
        button.setGraphic(view);
        return button;
    }

    public Button getNextButton() {return next;}
    public Button getBackButton() {return back;}
}
