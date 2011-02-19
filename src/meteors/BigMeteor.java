package meteors;
import age.*;
import java.awt.Color;
import java.awt.Graphics2D;
public class BigMeteor extends GameObject implements CustomShape{

    private Shape[] shape;
    private final double radius = 200;
    private double angle;

    public BigMeteor(){
        x = 200;
        y = 300;
        shape = Shape.upperHalfOvalShape((int)(x + radius/2), (int)(y + radius/2), radius);
    }

    @Override
    public void update() {
//        for(int i = 0; i < shape.length; i++)
//            shape[i].x--;
//        x--;
        if(Keyboard.keyPressed(Keyboard.VK_SPACE)){
            angle += Math.PI / 180;
            shape = Shape.rotatedUpperOval((int)(x + radius/2), (int)(y + radius/2), radius, angle);
        }
    }

    @Override
    public void render(Graphics2D g) {
        for(int i = 0; i < shape.length; i++)
            shape[i].render(g, Color.black);
    }

    public Shape[] getShape() {
        return shape;
    }

}
