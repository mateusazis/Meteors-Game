package meteors;
import age.*;
import java.awt.Graphics2D;
import java.util.LinkedList;
public class Meteor extends GameObject{

    private static final Sprite sprite = new Sprite("sprites/meteor.png");
    private int speedX = 1;
    public double angle;
    boolean exploding = false;
    private Timer explosionTimer;
    private Fragment frags[];

    public Meteor(int x, int y){
        this.x = x;
        this.y = y;
        height = 50;
        width = 50;
        speedX = 1 + (int)(Math.random() * 3);
        explosionTimer = new Timer(0, 0, 3, 0);
        frags = new Fragment[0];
    }

    public void setSpeed(int newSpeed){
        this.speedX = newSpeed;
    }

    @Override
    public void update() {
        if(!exploding){
            x -= speedX;
            if(angle < - Math.PI * 2)
                angle = 0;
            else
                angle -= Math.PI / 180;
        } else {
            for(int i = 0; i < frags.length; i++)
                frags[i].update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(!exploding)
            sprite.render(g, x, y);
        else{
            for(int i = 0; i < frags.length; i++)
                frags[i].rotateAndRender(g, frags[i].angle);
        }
    }

    public boolean collided(GameObject object){
        return !exploding && Collision.collided(this, object);
    }

    public void explode(){
        exploding = true;
        explosionTimer.start();
        frags = new Fragment[3];
        for(int i = 0; i < 3; i++)
            frags[i] = new Fragment(x, y);
        //create item
//        MeteorHandler.items.add(new UltraBomb(x, y));
        MeteorHandler.items.add(new FuelTank(x, y));
    }

    public boolean explosionComplete(){
        return explosionTimer.finished();
    }

    public static class Fragment extends GameObject{

        private static byte generalID = 0;
        private static Sprite sprites[];
        private byte currentID;
        public double angle;
        private byte shockFrame = 0;
        private int meteorX;
        private int meteorY;
        private int spriteID;
        private static LinkedList<Integer> availableIDs;

        public Fragment(int x, int y){
            this.x = x;
            this.y = y;
            height = 50;
            width = 50;
            currentID = generalID++;
            if(generalID > 2)
                generalID = 0;
            meteorX = x;
            meteorY = y;
            int index = (int)(Math.random() * availableIDs.size());
            spriteID = availableIDs.get(index);
            availableIDs.remove(index);
            if(availableIDs.size() == 0){
                availableIDs.add(0);
                availableIDs.add(1);
                availableIDs.add(2);
            }
        }

        @Override
        public void update() {
            switch(currentID){
                case 0:
                    x--;
                    y--;
                    angle -= Math.PI / 180;
                    break;
                case 1:
                    x++;
                    y--;
                    angle += Math.PI / 180;
                    break;
                case 2:
                    y++;
                    angle += Math.PI / 180;
                    break;
            }
            if(shockFrame == 0)
                shockFrame = 1;
            else
                shockFrame = 0;
        }

        @Override
        public void render(Graphics2D g) {
            sprites[spriteID].render(g, x, y);
        }

        public static void loadSprites(){
            sprites = new Sprite[3];
            for(int i = 0; i < 3; i++)
                sprites[i] = new Sprite("sprites/frag" + i + ".png");
            availableIDs = new LinkedList<Integer>();
            availableIDs.add(0);
            availableIDs.add(1);
            availableIDs.add(2);
        }
    }
}