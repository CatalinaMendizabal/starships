package model.components.data;

import controller.BulletController;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class BulletControllerData implements Serializable {
    List<BulletData> bullets;

    public BulletController toBulletController() {return new BulletController(this.bullets.stream().map(BulletData::toBullet).collect(Collectors.toList()));}
}
