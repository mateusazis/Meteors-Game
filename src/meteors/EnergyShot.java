package meteors;
import age.*;
import java.awt.Graphics2D;
public class EnergyShot extends GameObject{

    Player player;
    private Sprite sprite;

    public EnergyShot(Player p){
        player = p;
        x = p.x + p.width;
        y = p.y + p.height / 2;
        width = 50;
        height = 10;
        sprite = new Sprite("sprites/energy_shot.png");
    }

    @Override
    public void update() {
        x += Const.ENERGY_SHOT_SPEED;
    }

    @Override
    public void render(Graphics2D g) {
//        g.setColor(Color.green);
//        g.fillRect(x, y, width, height);
        sprite.render(g, x, y);
    }
}