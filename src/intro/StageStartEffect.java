package intro;
import age.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class StageStartEffect{

    private static final Color clearRed = new Color(1F, 0, 0, 0.7F);
    private static final Color veryClearRed = new Color(1F, 0, 0, 0.3F);
    private static final Font subTextFont = new Font("Impact", Font.ITALIC, 250);
    private static final Font mainFont = new Font("Impact", Font.ITALIC, 100);

    private boolean showText, showOtherText;
    private String stageName;

    public float height, heightVariation, whiteBarsY, mainTextX,
            textVariation, text2x, text3x;
    private final float maxTextX = 270F;
    private Runner effectRunner;
    private final float subTextVariation = 0.1F;
    private Timer finishTimer;
    

    public StageStartEffect(String stageName){
        this.stageName = stageName;
        heightVariation = 600F / 3000;
        showText = false;
        showOtherText = false;
        mainTextX = -300F;
        effectRunner = new Runner();
        textVariation = (maxTextX - mainTextX) / 1000;
        text2x = 0F;
        text3x = 400F;
    }

    public void start(){
        TaskRunner.scheduleTask(effectRunner, "stage_start_effect", 1);
    }

    public boolean rectsDone(){
        return height >= 650;
    }

    private boolean mainTextDone(){
        return mainTextX >= maxTextX;
    }

    private void enterRects(){
        if(!rectsDone())
            height += heightVariation;
        else {
            height = 600;
            effectRunner.setAction(Runner.ENTER_MAIN_TEXT);
            showText = true;
        }
    }

    public void enterMainText(){
        if(!mainTextDone())
            mainTextX += textVariation;
        else {
            showOtherText = true;
            effectRunner.currentAction = Runner.ENTER_OTHER_TEXT;
        }
    }

    public void enterOtherText(){
        text2x += subTextVariation;
        text3x -= subTextVariation;
        if(subTextDone()){
            effectRunner.currentAction = Runner.GO_AWAY;
            showOtherText = false;
        }
    }

    private void goAway(){
        if(!done())
            whiteBarsY+= heightVariation;
        else
            TaskRunner.cancelScheduledTask("stage_start_effect");
    }

    public boolean done(){
        return whiteBarsY >= 610;
    }

    private boolean subTextDone(){
        return text3x <= 0;
    }
    
    public void render(Graphics2D g) {
        if(!done()){
            for(int x = 0; x <= 600; x += 200){
                g.setColor(Color.white);
                g.fillRect(x, (int)whiteBarsY, 100, (int)height);
            }
            for(int x = 100; x <= 700; x += 200){
                g.setColor(Color.black);
                g.fillRect(x, (int)(600 - height - whiteBarsY), 100, (int)height);
            }
            if(showText){
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setFont(mainFont);
                g.setColor(Color.red);
                g.drawString(stageName, mainTextX, 350F);
                if(showOtherText){
                    g.setFont(subTextFont);
                    g.setColor(veryClearRed);
                    g.drawString(stageName, text2x, 200F);
                    g.setColor(clearRed);
                    g.drawString(stageName, text3x, 550F);

                }
            }
        }
    }

    private class Runner implements ParallelTask{

        private byte currentAction;
        public static final byte ENTER_RECT = 0;
        public static final byte ENTER_MAIN_TEXT = 1;
        public static final byte ENTER_OTHER_TEXT = 2;
        public static final byte GO_AWAY = 3;

        public void setAction(byte actionID){
            currentAction = actionID;
        }

        public void run() {
            switch(currentAction){
                case ENTER_RECT:
                    enterRects();
                    break;
                case ENTER_MAIN_TEXT:
                    enterMainText();
                    break;
                case ENTER_OTHER_TEXT:
                    enterOtherText();
                    break;
                case GO_AWAY:
                    goAway();
                    break;
            }
        }
    }
}