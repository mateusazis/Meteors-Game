package meteors;
import age.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MessageBox extends GameObject{

    public static final int MESSAGE_DURATION = 4;
    private static final int minimumAlfa = 100;
    private static final int lineLenght = 33;
    private final static Font defaultFont = new Font("Gulim", Font.PLAIN, 30);
    public final static byte CAPTAIN_NORMAL = 0, CAPTAIN_HAPPY = 1, CAPTAIN_TENSE = 2,
                             THAN_NORMAL = 3, THAN_DANGER = 4, THAN_HIPNO = 5;
    
    private int maxI, alfa;
    private float percentage;
    private boolean showing = false;
    private String text  = "", text2 = "";
    private Timer timer;
    private Sprite faceSprite;

    public MessageBox(){
        width = Const.MESSAGE_WINDOW_WIDTH;
        height = Const.MESSAGE_WINDOW_HEIGHT;
        x = 400 - width / 2;
        y = 600 - height - 50;
        maxI = y + height;
    }

    public boolean showing(){
        return showing;
    }

    @Override
    public void update() {
        if(showing && timer.finished()){
            showing = false;
            text = "";
            text2 = "";
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(showing){
            alfa = 0;
            percentage = 0;
            for(int i = y; i < maxI; i++){
                percentage = 1 - ((i-y) / 100.0F);
                alfa = minimumAlfa + (int)(percentage * (255 - minimumAlfa));
                g.setColor(new Color(0, 0, 255, alfa));
                g.fillRect(x, i, width, 1);
            }
            faceSprite.render(g, x + 10, y + 10);
            g.setColor(Color.white);
            g.setFont(defaultFont);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawString(text, x + height, y + 40);
            if(!text2.equals(""))
                g.drawString(text2, x + height, y + 70);
        }
    }

    public void display(String text, byte spriteID){
        setText(text);
        setSprite(spriteID);
        timer = new Timer(0, 0, MESSAGE_DURATION, 0);
        timer.start();
        showing = true;
    }

    private void setText(String text){
        if(text.length() <= lineLenght)
            this.text = text;
        else{
            String words[] = text.split(" ");
            boolean limitReached = false;
            for(int i = 0; i < words.length; i++){
                if(!limitReached){
                    if(this.text.length() + words[i].length() > lineLenght){
                        limitReached = true;
                        i--;
                    }
                    else
                        this.text += words[i] + " ";
                } else
                        this.text2 += words[i] + " ";
            }
        }
    }

    private void setSprite(byte spriteID){
        switch(spriteID){
            case CAPTAIN_NORMAL:
                faceSprite = new Sprite("sprites/captain_normal.png");
                break;
            case CAPTAIN_HAPPY:
                faceSprite = new Sprite("sprites/captain_happy.png");
                break;
            case CAPTAIN_TENSE:
                faceSprite = new Sprite("sprites/captain_tense.png");
                break;
            case THAN_NORMAL:
                faceSprite = new Sprite("sprites/than_normal.png");
                break;
            case THAN_DANGER:
                faceSprite = new Sprite("sprites/than_danger.png");
                break;
            case THAN_HIPNO:
                faceSprite = new Sprite("sprites/than_hipno.png");
                break;
        }
    }

}
