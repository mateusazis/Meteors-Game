package meteors;
import age.*;
import age.Keyboard;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
public class GameOver implements GameController{

    String message;

    public void load() {
        message = "Game Over :(";
    }

    public void start(){

    }

    public void unload(){

    }

    public void update() {
        if(Keyboard.keyPressed(Keyboard.VK_ESCAPE))
            System.exit(0);
    }

    public void render(Graphics2D g) {
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);
            g.setColor(Color.white);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
            g.drawString(message, 300, 200);
    }
}
