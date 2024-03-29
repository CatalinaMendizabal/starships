package UI;

import controller.GameController;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.RootSetter;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Player;

import java.io.IOException;

public class GameOverUI {

    GameController gameController;

    public GameOverUI(GameController gameController) {this.gameController = gameController;}

    public void display(RootSetter rootSetter, Pane pane) throws IOException {
        if (rootSetter != null) rootSetter.setRoot(gameOver(pane));
    }

    public Parent gameOver(Pane pane) throws IOException {
        ImageLoader imageLoader = new ImageLoader();
        Image background = imageLoader.loadFromResources("background.jpeg", 2000, 2000);
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        pane.getChildren().addAll(backgroundView);
        displayInformation(pane);
        return pane;
    }

    public void displayInformation( Pane pane) {
        Text text = new Text("GAME OVER!");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(20, Color.BLACK));
        text.setX(150);
        text.setY(100);

        Text text2 = new Text("The winner is player " + (gameController.getWinnerIndex() + 1));
        text2.setFont(Font.font("Verdana", 30));
        text2.setFill(Color.WHITE);
        text2.setEffect(new DropShadow(30, Color.BLACK));
        text2.setX(150);
        text2.setY(150);

        Text text3 = new Text("With total score of: " + gameController.getWinnerScore() + " points!");
        text3.setFont(Font.font("Verdana", 30));
        text3.setFill(Color.WHITE);
        text3.setEffect(new DropShadow(30, Color.BLACK));
        text3.setX(150);
        text3.setY(200);

        Button exit = new IntroductionButton("Exit", 150, 300).getButton();
        exit.setOnMouseClicked(e -> {System.exit(0);});

        pane.getChildren().addAll(text, text2, text3, exit);
    }

}


