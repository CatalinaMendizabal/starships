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
    private String imageName;
    private Ship ship;
    private BulletControllerData bulletController;

    @SneakyThrows
    public ShipController toShipController() {
        ImageLoader imageLoader = new ImageLoader();
        ShipView shipView = new ShipView(imageLoader.loadFromResources(imageName, 100, 100), 100, 100);
        shipView.getImageView().setRotate(ship.getDirectionAngle());
        return ShipController.builder()
                .shipView(shipView)
                .ship(ship)
                .bulletController(bulletController.toBulletController())
                .build();
    }
}
