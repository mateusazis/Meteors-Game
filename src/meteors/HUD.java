package meteors;
import age.GameObject;
import java.awt.Color;
import java.awt.Graphics2D;

public class HUD extends GameObject{

    Player player;
    private final static int fuelWidth = 75;
    
    public HUD(Player p){
        player = p;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.red);
        g.drawString("Lives: " + player.lives, 15, 25);
        g.drawString("Score: " + player.score, 650, 25);
        g.drawString("Fuel: ", 15, 45);
        g.setColor(Color.black);
        g.fillRect(50, 45, fuelWidth, 10);
        g.setColor(Color.white);
        g.fillRect(50, 45, (int)(fuelWidth * (player.fuel / 100.0)), 10);
    }
}