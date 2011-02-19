package intro;
import age.GameObject;
import age.ParallelTask;
import age.TaskRunner;
import age.Sprite;
import java.awt.Graphics2D;

public class EnergyHole extends GameObject implements ParallelTask{

    Sprite s;
    private int currentFrame;

    public EnergyHole(){
        x = 1000;
        y = 225;
        height = 150;
        width = 50;
        s = new Sprite("sprites/energy_hole.png", 200, 200);
        TaskRunner.scheduleTask(this, "holeUpdater", 500);
    }

    @Override
    public void update() {
        if(x >= 600)
            x--;
    }

    @Override
    public void render(Graphics2D g) {
//        g.setColor(Color.black);
//        g.fillRect(x, y, width, height);
        s.render(g, x, y);
    }

    public void run() {
        currentFrame++;
        if(currentFrame == 4)
            currentFrame = 0;
        s.setCurrentFrame(currentFrame);
    }

}
