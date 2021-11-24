package data;

import controller.BulletController;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SaveBulletController implements Serializable {
    List<SaveBullet> bullets;

    public BulletController toBulletController() {
        return new BulletController(this.bullets.stream().map(SaveBullet::toBullet).collect(Collectors.toList()));
    }
}
