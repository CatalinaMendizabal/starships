package controller.command;

import controller.ShipController;

public abstract class Command {
    private int keyCode;
    protected ShipController sc;

    public Command(ShipController sc, int keyCode){
        this.sc = sc;
        this.keyCode = keyCode;
    }

    public abstract void execute();

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode){
        this.keyCode = keyCode;
    }
}
