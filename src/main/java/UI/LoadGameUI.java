package UI;

import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;

public class LoadGameUI {

    ImageLoader imageLoader = new ImageLoader();

    public void generateGameUI(Pane pane) throws IOException {
        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        pane.setBackground(new Background(backgroundImage));
    }

    public ImageLoader getImageLoader() {return imageLoader;}
}
