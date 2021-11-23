package data;

import controller.ShipController;
import edu.austral.dissis.starships.file.ImageLoader;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import view.entity.ShipView;

import java.io.Serializable;

@Data
@Builder
public class SaveShipController implements Serializable {
    private SaveShip ship;
    private SaveBulletController bulletController;

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
