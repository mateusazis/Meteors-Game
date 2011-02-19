package meteors;
import age.*;
import java.awt.Graphics2D;
import java.util.LinkedList;
public class MeteorHandler extends GameObject{

    private Player p;
    private Timer timer;
//    private LinkedList<Meteor> meteors;
    protected static LinkedList<Meteor> meteors;
    protected static LinkedList<Item> items = new LinkedList<Item>();
    private int maxMeteors;

    public MeteorHandler(Player player, int maxMeteors){
        x = 0;
        y = 0;
        p = player;
        this.maxMeteors = maxMeteors;
        meteors = new LinkedList<Meteor>();
        addMeteor();
    }

    @Override
    public void update() {
        if(timer.finished() && meteors.size() < maxMeteors)
            addMeteor();
        for(int i = 0; i < meteors.size(); i++){
            Meteor currentMeteor = meteors.get(i);
            if(currentMeteor.explosionComplete()){
                meteors.remove(i);
                addMeteor();
            } else if(p.shotHit(currentMeteor)) {
                p.score++;
                p.energyShot = null;
                currentMeteor.explode();
                break;
            } else if(currentMeteor.x + currentMeteor.width < 0) {
                meteors.remove(i);
                p.score++;
                addMeteor();
            } else {
                currentMeteor.update();
                if(currentMeteor.collided(p)){
                    p.die();
                    meteors.remove(i);
                    addMeteor();
                }
            }
        }
        for(int i = 0; i < items.size(); i++){
            Item currentItem = items.get(i);
            if(Collision.collided(p, currentItem)){
                currentItem.getItem(p);
                items.remove(i);
            } else {
                currentItem.update();
                if(currentItem.disappeared())
                    items.remove(i);
            }
        }
    }

    private void addMeteor(){
        int my = (int)(Math.random() * 550);
        int mx = 800;
        Meteor m = new Meteor(mx, my);
        meteors.add(m);
        if(timer != null)
            timer.stop();
        timer = new Timer(0, 0, 2, 0);
        timer.start();
    }

    @Override
    public void render(Graphics2D g) {
        for(int i = 0; i < meteors.size(); i++){
            Meteor currentMeteor = meteors.get(i);
            if(!currentMeteor.exploding)
                currentMeteor.rotateAndRender(g, currentMeteor.angle);
            else
                currentMeteor.render(g);
        }
        for(int i = 0; i < items.size(); i++){
            Item currentItem = items.get(i);
            currentItem.rotateAndRender(g, currentItem.getAngle());
        }

//        for(int i = 0; i < items.size(); i++)
//            items.get(i).render(g);
    }

}
