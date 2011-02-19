package meteors;
import age.*;
import java.awt.Graphics2D;
public class FuelTank extends Item{

    private Timer showingTime;
    private double angle;
    private double angleVariation = Math.PI / 360;

    public FuelTank(int x, int y){
        sprite = new Sprite("sprites/fuel_tank_large.png");
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
        showingTime = new Timer(0, 0, 3, 0);
        showingTime.start();
    }

    public boolean disappeared(){
        return showingTime.finished();
    }

    @Override
    public void update(){
        if(Keyboard.keyPressed(Keyboard.VK_A))
            sprite = new Sprite("sprites/fuel_tank_small.png");
        if(Keyboard.keyPressed(Keyboard.VK_S))
            sprite = new Sprite("sprites/fuel_tank_large.png");
        angle += angleVariation;
        if(angle >= Math.PI / 4 || angle <= -Math.PI / 4)
            angleVariation *= -1;
    }

    @Override
    public void render(Graphics2D g){
        if(!disappeared()){
            sprite.render(g, x, y);
        }
    }

    @Override
    public void getItem(Player player) {
        if(player.fuel <= 70)
            player.fuel += 30;
        else
            player.fuel = 100;
    }

    @Override
    public double getAngle(){
        return angle;
    }

}
