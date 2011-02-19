package meteors;
import age.*;
import java.awt.Graphics2D;
public class Background extends GameObject{

    private Sprite s;
    private Player p;
    private final int scaleSpeed = 2;
    private int maxPlayerX;

    public Background(String path, Player player){
        p = player;
        s = new Sprite(path);
    }

    @Override
    public void update() {
        if(p.x > maxPlayerX)
            maxPlayerX = p.x;
        x = - maxPlayerX / scaleSpeed;
//        y = 6 - p.y / scaleSpeed;
    }

    @Override
    public void render(Graphics2D g) {
        s.render(g, x, y);
    }



}
