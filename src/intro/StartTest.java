package intro;
import age.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class StartTest implements GameController{

    StageStartEffect effect;

    public void load() {
        effect = new StageStartEffect("Stage 1");
    }

    public void start() {
        effect.start();
    }

    public void update() {
        
    }

    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, 800, 600);
        effect.render(g);
    }

    public void unload() {
        
    }

}
