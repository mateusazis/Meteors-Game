package meteors;
import age.*;
import java.awt.Color;
import java.awt.Graphics2D;
public class BigMeteorTest implements GameController{

    BigMeteor meteor;

    public void load() {
        meteor = new BigMeteor();
    }

    public void start() {

    }

    public void update() {
        meteor.update();
    }

    public void render(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 800, 600);
        meteor.render(g);
    }

    public void unload() {
        
    }

    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        engine.addGameController(0, new BigMeteorTest());
        engine.setSpeed(5);
        engine.run();
    }

}
