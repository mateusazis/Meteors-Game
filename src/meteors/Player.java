package meteors;
import age.*;
import java.awt.Graphics2D;

public class Player extends GameObject{

    public UltraBomb bomb;
    private int speedX = 1, speedY = 1;
    private Sprite s;
    protected int lives = 5;
    private boolean blinking = false;
    protected int score;
    protected byte fuel;
    private byte blinks;
    private Timer blinkTimer, fuelTimer;
    EnergyShot energyShot;

    public Player(){
         x = 10;
         y = 300;
         width = 100;
         height = 50;
         s = new Sprite("sprites/ship.png", 100, 50);
         fuel = 100;
         fuelTimer = new Timer(0, 0, 0, 500);
         fuelTimer.start();
    }

    public void die(){
        blinkTimer = new Timer(0, 0, 0, 200);
        blinkTimer.start();
        blinking = true;
        lives--;
        x = 10;
        y = 300;
        fuel = 100;
    }

    @Override
    public void update() {
        move();
        if(blinking && blinkTimer.finished()){
            blinks++;
            blinkTimer = new Timer(0, 0, 0, 200);
            blinkTimer.start();
        }
        if(blinks > 6){
            blinking = false;
            blinks = 0;
        }

        if(Keyboard.keyTyped(Keyboard.VK_SPACE) && energyShot == null)
            energyShot = new EnergyShot(this);
        if(energyShot != null){
            energyShot.update();
            if(energyShot.x > 800)
                energyShot = null;
        }
        if(fuelTimer.finished() && fuel > 0){
            fuel -= Const.FUEL_VARIATION;
            fuelTimer = new Timer(0, 0, 0, 500);
            fuelTimer.start();
        }
        if(bomb != null && Keyboard.keyTyped(Keyboard.VK_CONTROL)){
            bomb.use();
            bomb = null;
        }
//        if(fuel <= 0)
//            die();
    }

    private void move(){
        boolean moved = false;

        if(Keyboard.keyPressed(Keyboard.VK_UP) && y > 0){
            y -= speedY;
            moved = true;
        }
        if(Keyboard.keyPressed(Keyboard.VK_DOWN) && y + height < 600){
            y += speedY;
            moved = true;
        }
        if(Keyboard.keyPressed(Keyboard.VK_LEFT) && x > 0){
            x -= speedX;
            moved = true;
        }
        if(Keyboard.keyPressed(Keyboard.VK_RIGHT) && x + width < 800){
            x += speedX;
            moved = true;
        }
        if(moved)
            s.setCurrentFrame(1);
        else
            s.setCurrentFrame(0);
    }

    @Override
    public void render(Graphics2D g) {
        if(energyShot != null)
            energyShot.render(g);
        if(!blinking())
            s.render(g, x, y);
//        if(!blinking)
    }

    public boolean blinking(){
        return blinking && blinks % 2 != 0;
    }

    public boolean shotHit(Meteor m){
        return energyShot != null && m.collided(energyShot);
    }

}
