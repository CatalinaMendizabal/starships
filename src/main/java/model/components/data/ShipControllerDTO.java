package model.components.data;

import controller.ShipController;
import edu.austral.dissis.starships.file.ImageLoader;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import model.components.data.BulletControllerDTO;
import model.components.data.ShipDTO;
import view.ShipView;

import java.io.Serializable;

@Data
@Builder
public class ShipControllerDTO implements Serializable {
    private String imageName;
    private ShipDTO ship;
    private BulletControllerDTO bulletController;

    @SneakyThrows
    public ShipController toShipController() {
        ImageLoader imageLoader = new ImageLoader();
        ShipView shipView = new ShipView(imageLoader.loadFromResources(imageName, 100, 100), (int) ship.getPosX(), (int) ship.getPosY());
        shipView.getImageView().setRotate(ship.getAngle());
        return ShipController.builder()
                .shipView(shipView)
                .ship(ship.toShip())
                .bulletController(bulletController.toBulletController())
                .build();
    }
}
