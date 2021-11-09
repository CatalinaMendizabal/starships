package UI;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class IntroductionButton {
    Button btn = new Button();
    DropShadow dropShadow = new DropShadow();
    String text;
    int posX, posY;

    public IntroductionButton(String text, int posX, int posY) {
        this.text = text;
        this.posX = posX;
        this.posY = posY;
    }

    public Button getButton() {
        btn.setText(text);
        btn.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        btn.setTranslateX(posX);
        btn.setTranslateY(posY);
        btn.setPrefWidth(200);
        btn.setPrefHeight(50);
        btn.setStyle("-fx-background-color: #5A2363; -fx-text-fill: #ffffff;");
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        btn.setEffect(dropShadow);
        return btn;
    }
}
