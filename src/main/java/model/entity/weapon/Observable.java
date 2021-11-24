package model.entity.weapon;

public interface Observable {

    void attachManager(BulletManager observer);

    void detachManager(BulletManager observer);

}
