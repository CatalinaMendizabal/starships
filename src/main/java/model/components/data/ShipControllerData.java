package model.components.data;

import controller.ShipController;
import edu.austral.dissis.starships.file.ImageLoader;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import model.components.Ship;
import view.ShipView;

import java.io.Serializable;

@Data
@Builder
public class ShipControllerData implements Serializable {

    private ShipData ship;
    private BulletControllerData bulletController;

    @SneakyThrows
    public ShipController toShipController() {
        ImageLoader imageLoader = new ImageLoader();
        ShipView shipView = new ShipView(imageLoader.loadFromResources("starship.png", 100, 100), (int) ship.getPosX(), (int) ship.getPosY());
        shipView.getImageView().setRotate(ship.getAngle());

        return ShipController.builder()
                .shipView(shipView)
                .ship(ship.toShip())
                .bulletController(bulletController.toBulletController())
                .build();
    }
}
