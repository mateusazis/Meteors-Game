package intro;
import age.GameController;
import age.ParallelTask;
import age.TaskRunner;
import age.Sprite;
import age.Timer;
import age.Collision;
import age.GameEngine;
import age.advanced.VisualEffect;
import java.awt.Color;
import meteors.Player;
import meteors.Meteor;
import meteors.MessageBox;
import java.awt.Graphics2D;

public class SecondIntro implements GameController, ParallelTask{

    private Sprite background, fuel;
    private Player player;
    private short step;
    private Meteor m;
    private MessageBox box;
    private Timer timer, shakeTimer;
    private int screenY = 10;
    private VisualEffect fadeIn;
    public static StageStartEffect startEffect;
    public static boolean passedIntro = false;

    public void load() {
        background = new Sprite("sprites/space.jpg");
        player = new Player();
        m = new Meteor(800, 300);
        box = new MessageBox();
        Meteor.Fragment.loadSprites();
        fadeIn = VisualEffect.fadeIn(Color.white, 2500);
        fuel = new Sprite("sprites/fuel.png", 100, 50);
        startEffect = new StageStartEffect("Stage 1");
    }

    public void resetTimer(int sec, int msec){
        timer = new Timer(0, 0, sec, msec);
        timer.start();
    }

    public void update() {
        box.update();
        switch(step){
            case 0:
                if(fadeIn.finished())
                    step++;
                break;
            case 1:
                box.display("O que houve?", MessageBox.THAN_NORMAL);
                step++;
                break;
            case 2:
                if(!box.showing()){
                    box.display("Eu não sei. Por que tudo parece tão... detalhado...?", MessageBox.CAPTAIN_NORMAL);
                    step++;
                }
                break;
            case 3:
                if(!box.showing()){
                    box.display("METEORO, CAPITÃO!!", MessageBox.THAN_DANGER);
                    resetTimer(1, 0);
                    step++;
                }
                break;
            case 4:
                if(timer.finished()){
                    if(Collision.collided(player, m)){
                        m.explode();
                        shakeScreen();
                        step++;
                    }
                    m.update();
                }
                break;
            case 5:
                if(shakeTimer.finished()){
                    TaskRunner.cancelScheduledTask("shaker");
                    TaskRunner.scheduleTask(new FuelUpdater(fuel), "fuelUpdater", 250);
                    box.display("Capitão, fomos atingidos. Vazamento de combustível detectado!", MessageBox.THAN_NORMAL);
                    step++;
                }
                m.update();
                break;
            case 6:
                if(!box.showing()){
                    box.display("Precisamos de algum lugar seguro para reparos. Than?", MessageBox.CAPTAIN_NORMAL);
                    step++;
                }
                break;
            case 7:
                if(!box.showing()){
                    box.display("Há um planeta bem perto. Parece habitável...", MessageBox.THAN_NORMAL);
                    step++;
                }
                break;
            case 8:
                if(!box.showing()){
                    box.display("Ótimo. Me dê os controles e se segure...", MessageBox.CAPTAIN_NORMAL);
                    step++;
                }
                break;
            case 9:
                if(!box.showing()){
                    startEffect.start();
                    resetTimer(3, 0);
                    step++;
                }
                break;
            case 10:
                if(timer.finished()){
                    IntroScene.music.stop();
                    System.out.println("LOADING NEXT");
                    GameEngine.getInstance().runNextGameController(0);
                }
                break;
        }
    }

    public void shakeScreen(){
        shakeTimer = new Timer(0, 0, 3, 0);
        shakeTimer.start();
        TaskRunner.scheduleTask(this, "shaker", 20);
    }

    public void render(Graphics2D g) {
        if(step == 5)
            g.translate(0, screenY);
        background.render(g, 0, 0);
        player.render(g);
        m.rotateAndRender(g, m.angle);
        box.render(g);
        if(step == 0)
            fadeIn.render(g);
        if(step > 5)
            fuel.render(g, player.x, player.y + 25);
        if(step >= 9)
            startEffect.render(g);
    }

    public void run() {
        //reducing shake

        //const shake
        if(screenY >= 0){
            screenY = -10;
        }
        else
            screenY = 10;
    }

    public void start() {
        fadeIn.start();
        passedIntro = true;
        m.setSpeed(3);
    }

    public void unload() {
        background = null;
        box = null;
        fadeIn = null;
        fuel = null;
        m = null;
        player = null;
        shakeTimer = null;
        timer = null;
    }

    private static class FuelUpdater implements ParallelTask{

        Sprite s;
        int frame;

        public FuelUpdater(Sprite s){
            this.s = s;
        }

        @Override
        public void run(){
            frame++;
            if(frame > 2)
                frame = 0;
            s.setCurrentFrame(frame);
        }

    }

}
