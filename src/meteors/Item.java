package meteors;
import age.GameObject;
import age.Sprite;

public abstract class Item extends GameObject{

    Sprite sprite;

    public abstract void getItem(Player player);
    public abstract boolean disappeared();
    public abstract double getAngle();
}
