package meteors;
import age.*;
import java.awt.Graphics2D;
import intro.SecondIntro;

public class Stage1 implements GameController, ParallelTask{

    private Background bg;
    private Player p;
    private HUD h;
    private MeteorHandler handler;
    private Sound music;
    MessageBox m;
    boolean musicStarted;
    boolean loaded;

    public void load() {
        loaded = false;
        TaskRunner.runTask(this);
    }

    public void start(){

    }

    public void unload(){
        bg = null;
        p = null;
        h = null;
        handler = null;
        music.stop();
        music = null;
        m = null;
    }

    public void update() {
        if(loaded){
            if(!SecondIntro.passedIntro || SecondIntro.startEffect.done()){
                if(!musicStarted){
                    music.playAndRepeat();
                    musicStarted = true;
                }
                if(p.lives == 0)
                    GameEngine.getInstance().runNextGameController(1);
                bg.update();
                p.update();
                h.update();
                handler.update();
                if(Keyboard.keyPressed(Keyboard.VK_C))
                    GameEngine.getInstance().runNextGameController(1);
            }
        }
    }

    public void render(Graphics2D g) {
        if(loaded){
            bg.render(g);
            p.render(g);
            h.render(g);
            handler.render(g);
            m.render(g);
        }
        if(SecondIntro.passedIntro)
            SecondIntro.startEffect.render(g);
    }

    public void run() {
        p = new Player();
        bg = new Background("sprites/space.jpg", p);
        h = new HUD(p);
        handler = new MeteorHandler(p, 10);
        Meteor.Fragment.loadSprites();
        music = new Sound("sounds/intro_velha.wav");
//        music.play();
//        music.playAndRepeat();
        m = new MessageBox();
        musicStarted = false;
        loaded = true;
    }
}