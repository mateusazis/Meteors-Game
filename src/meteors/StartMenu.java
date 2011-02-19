package meteors;
import age.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class StartMenu implements GameController{

    MP3 bgm;
    Sprite background;
    private int step;
    Timer timer;
    Met mets[][];
    Sprite ship;
    int shipX;
    int shipY;

    public void load() {
        bgm = new MP3("march.mp3");
        background = new Sprite("opening.jpg");
        bgm.play();
        timer = new Timer(0, 0, 3, 0);
        ship = new Sprite("ship.png", 100, 50);
        ship.setCurrentFrame(1);
        mets = new Met[5][5];
        timer.start();
    }

    public void start(){

    }

    public void unload(){

    }

    public void update() {
        if(Keyboard.keyPressed(0)){
            bgm.stop();
            GameEngine.getInstance().runNextGameController(0);
        }
        if(timer.finished()){
            switch(step){
                case 0:
                    mets[1][1] = new Met(375, 275);
                    resetTimer();
                    break;
                case 1:
                    for(int i = 1; i <= 3; i++)
                        for(int j = 1; j <= 3; j++){
                            mets[i][j] = new Met(j * 50 + 275, i * 50 + 175);
                        }
                    resetTimer();
                    break;
                case 2:
                    for(int i = 0; i < 5; i++)
                        for(int j = 0; j < 5; j++){
                            if(i != 1 || j != 1){
                                mets[i][j] = new Met(j * 50 + 275, i * 50 + 175);
                            }
                        }
                    resetTimer();
                    break;
            }
            if(step < 4)
                step++;
        }
        if(step > 0){
            Met.angle += Math.PI / 180;
            for(int i = 0; i < 5; i++)
                for(int j = 0; j < 5; j++)
                    if(mets[i][j] != null)
                        mets[i][j].update();
        }
        if(step == 4)
            moveShip();
    }

    void moveShip(){

    }

    void resetTimer(){
        switch(step){
            case 0:
                timer = new Timer(0, 0, 2, 0);
                break;
            case 1:
                timer = new Timer(0, 0, 2, 0);
                break;
            case 2:
                timer = new Timer(0, 0, 3, 0);
                break;
        }
        timer.start();
    }

    public void render(Graphics2D g) {
        background.render(g, 0, 0);
        if(step > 0)
            for(int i = 0; i < 5; i++)
                for(int j = 0; j < 5; j++)
                    if(mets[i][j] != null)
                        mets[i][j].render(g);
        if(step >= 4)
            ship.render(g, shipX, shipY);
        g.setColor(Color.white);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        g.drawString("Press any key to begin...", 300, 500);
    }

    static class Met extends GameObject{

        public static double angle = 0;
        Sprite sprite;

        public Met(int x, int y){
            this.x = x;
            this.y = y;
            height = 50;
            width = 50;
            sprite = new Sprite("meteor.png");
        }

        @Override
        public void update() {
            sprite.rotate(angle, x + 25, y + 25);
        }

        @Override
        public void render(Graphics2D g) {
            sprite.render(g, x, y);
        }

    }

}