package intro;
import age.GameController;
import age.Sprite;
import age.Timer;
import age.MP3;
import age.Keyboard;
import age.GameEngine;
import age.advanced.VisualEffect;
import java.awt.Color;
import java.awt.Graphics2D;
import meteors.MessageBox;

public class IntroScene implements GameController{

    private MessageBox m;
    private Sprite backGround;
    private PlayerShip ship;
    private short step = 0;
    private Timer t;
    private EnergyHole hole;
    VisualEffect fadeOut;
    VisualEffect fadeIn;
//    public static Sound music;
    public Graphics2D g2;
    public static MP3 music;
    
    public void start() {
        fadeIn.start();
//        music.playAndRepeat();
        music.play();
    }

    public void unload() {
        m = null;
        backGround = null;
        ship = null;
        t = null;
        hole = null;
        fadeIn = null;
        fadeOut = null;
    }

    public void load() {
        backGround = new Sprite("sprites/introbg.png");
//        music = new Sound("sounds/Intro.wav");
        ship = new PlayerShip(-100, 300);
        m = new MessageBox();
        hole = new EnergyHole();
        fadeOut = VisualEffect.fadeOut(Color.white, 2000);
        fadeIn = VisualEffect.fadeIn(Color.black, 2500);
        music = new MP3("sounds/intro_fb.mp3");
    }

    public void resetTimer(int sec, int msec){
        t = new Timer(0, 0, sec, msec);
        t.start();
    }

    public void update() {
        if(Keyboard.keyTyped(Keyboard.VK_T))
            GameEngine.getInstance().getCanvas().toggleFullScreenAndScale();
        if(Keyboard.keyPressed(Keyboard.VK_ENTER))
            GameEngine.getInstance().runNextGameController(0);
        m.update();
        switch(step){
            case 0:
                if(fadeIn.finished()){
                    step++;
                    resetTimer(2, 0);
                    GameEngine.getInstance().setSpeed(15);
                }
                break;
            case 1:
                if(t.finished()){
                    step++;
                }
                break;
            case 2:
                ship.x++;
                if(ship.x >= 30){
                    step++;
                    GameEngine.getInstance().setSpeed(5);
                    resetTimer(1, 0);
                }
                break;
            case 3:
                if(t.finished()){
                    m.display("Capitão, detectei um objeto desconhecido adiante.", MessageBox.THAN_NORMAL);
                    resetTimer(MessageBox.MESSAGE_DURATION + 1, 0);
                    step++;
                }
                break;
            case 4:
                if(!m.showing() && t.finished()){
                    m.display("Com o que se parece?", MessageBox.CAPTAIN_NORMAL);
                    resetTimer(MessageBox.MESSAGE_DURATION + 1, 0);
                    GameEngine.getInstance().setSpeed(15);
                    step++;
                }
                break;
            case 5:
                hole.update();
                if(!m.showing() && t.finished()){
                    m.display("Ele é... é... lindo.", MessageBox.THAN_NORMAL);
                    resetTimer(MessageBox.MESSAGE_DURATION + 1, 0);
                    step++;
                }
                break;
            case 6:
                hole.update();
                if(!m.showing() && t.finished()){
                    m.display("Desvie o curso, Than.", MessageBox.CAPTAIN_NORMAL);
                    step++;
                }
                break;
            case 7:
                hole.update();
                if(!m.showing() && t.finished()){
                    m.display("Than?!", MessageBox.CAPTAIN_NORMAL);
                    resetTimer(MessageBox.MESSAGE_DURATION + 1, 0);
                    step++;
                }
                break;
            case 8:
                if(!m.showing() && t.finished()){
                    m.display("É... lindo...", MessageBox.THAN_HIPNO);
                    resetTimer(MessageBox.MESSAGE_DURATION + 1, 0);
                    step++;
                }
                break;
            case 9:
                if(!m.showing() && t.finished()){
                    m.display("THAN!!!!!!!!!!!!", MessageBox.CAPTAIN_TENSE);
                    resetTimer(MessageBox.MESSAGE_DURATION, 0);
                    fadeOut.start();
                    GameEngine.getInstance().setSpeed(5);
                    step++;
                }
                break;
            case 10:
                ship.rotate();
                if(fadeOut.finished())
                    GameEngine.getInstance().runNextGameController(-1);
                break;
        }
    }

    public void render(Graphics2D g) {
        backGround.render(g, 0, 0);
        if(step >= 5)
            hole.render(g);
        if(step < 10)
            ship.render(g);
        else
            ship.rotateAndRender(g, ship.angle);
        m.render(g);

        //  fadeIn/ fadeOut effects
        if(step == 0)
            fadeIn.render(g);
        if(step == 10)
            fadeOut.render(g);
    }
}