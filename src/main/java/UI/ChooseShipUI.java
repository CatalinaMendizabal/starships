package UI;

import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ChooseShipUI {
    ImageLoader imageLoader = new ImageLoader();

    public void generateShipUI(Pane pane) throws IOException {
        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);

        pane.getChildren().add(backgroundView);
       // pane.getChildren().addAll(text, start, loaded, exit);
    }
}
/*
ImageLoader imageLoader = new ImageLoader();

        Pane pane = new Pane();

        BackgroundImage myBI= new BackgroundImage(imageLoader.loadFromResources("background.png", 1920, 1080),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));

        MenuItem blue = new MenuItem("BLUE");
        blue.setOnMouseClicked(event -> Config.setShip(i, "starship.gif"));

        MenuItem green = new MenuItem("GREEN");
        green.setOnMouseClicked(event -> Config.setShip(i, "green-ship.png"));

        MenuItem next = new MenuItem("NEXT PLAYER");
        next.setOnMouseClicked(event -> {
            try {
                if(i == Config.PLAYERS - 1) rootSetter.setRoot(loadShipSelect(0, gameState));
                else rootSetter.setRoot(loadShipSelect(i + 1, gameState));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem back = new MenuItem("BACK TO MENU");
        back.setOnMouseClicked(event -> {
            try {
                rootSetter.setRoot(loadMenu(gameState));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuBox menu = new MenuBox("PLAYER: " + i, blue, green, next, back);

        pane.getChildren().add(menu);

        return pane;
 */
