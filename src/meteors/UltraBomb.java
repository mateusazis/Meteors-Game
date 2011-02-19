package meteors;
import age.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class UltraBomb extends Item implements ParallelTask{

    private double angle;
    private double angleVariation = Math.PI / 360;
    public LinkedList<Meteor> meteorList;

    public UltraBomb(int x, int y){
        this.x = x;
        this.y = y;
        height = 50;
        width = 50;
    }

    @Override
    public void getItem(Player player) {
        this.meteorList = MeteorHandler.meteors;
        player.bomb = this;
    }

    public void getItem(Player player, LinkedList<Meteor> meteors){
        this.meteorList = meteors;
        getItem(player);
    }

    @Override
    public boolean disappeared() {
        return false;
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public void update() {
        
    }

    public void use(){
        TaskRunner.runTask(this);
    }

    @Override
    public void run(){
        Meteor currentMeteor;
        boolean explosionsCalled = false;
        boolean allComplete = false;
        while(meteorList.size() > 0 && !allComplete){
            for(int i = 0; i < meteorList.size(); i++){
                currentMeteor = meteorList.get(i);
                if(!currentMeteor.exploding && !explosionsCalled){
                    currentMeteor.explode();
                    GameEngine.delay(500);
                } else{
                    explosionsCalled = true;
                        if(currentMeteor.explosionComplete()){
                        meteorList.remove(i);
                        i--;
                        allComplete = true;
                        }
                }
            }
        }
    }


    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }

}
