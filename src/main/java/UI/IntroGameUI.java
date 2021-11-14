package UI;

import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class IntroGameUI {

    Text text = generateTitle();
    Button start = new IntroductionButton("Start Game", 100, 150).getButton();
    Button loaded = new IntroductionButton("Continue Game", 100, 230).getButton();
    Button exit = new IntroductionButton("Exit Game", 100, 320).getButton();
    ImageLoader imageLoader = new ImageLoader();

    public void generateIntro(Pane pane) throws IOException {
        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        ImageView imageView = new ImageView(background);
        imageView.setFitWidth(1920);
        imageView.setFitHeight(1080);

        pane.getChildren().add(imageView);
        pane.getChildren().addAll(text, start, loaded, exit);
    }

    private Text generateTitle() {
        Text text = new Text("Welcome to STARSHIPS");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(20, Color.BLACK));
        text.setX(70);
        text.setY(100);
        return text;
    }

    public Button getStart() {return start;}

    public Button getLoaded() {return loaded;}

    public Button getExit() {return exit;}
}
