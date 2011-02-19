package intro;
import age.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class PlayerShip extends GameObject{

    double angle;

    public PlayerShip(int x, int y){
        this.x = x;
        this.y = y;
        height = 50;
        width = 100;
    }

    @Override
    public void update() {
        
    }

    public void rotate(){
        angle += Math.PI / 1000;
        x++;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(x, y + 15, width, height - 15);
        g.setColor(Color.red);
        g.fillRect(x, y, 20, height);
        g.setColor(Color.yellow);
        g.fillRect(x - 15, y + 10, 15, 5);
        g.fillRect(x - 15, y + 25, 15, 5);
        g.fillRect(x - 15, y + 40, 15, 5);
        g.setColor(Color.CYAN);
        g.fillRect(x + 70, y + 20, 20, 10);
    }

}
