package meteors;
import age.GameEngine;
import intro.IntroScene;
import intro.SecondIntro;
import intro.StartTest;
public class Main {


    public static void main(String[] args) {
        
        GameEngine game = new GameEngine();
//        game.addGameController(-3, new StartTest());
//        game.addGameController(-2, new IntroScene());
//        game.addGameController(-1, new SecondIntro());
//        game.addGameController(-1, new StartMenu());
        game.addGameController(0, new Stage1());
        game.addGameController(1, new GameOver());
        game.setSpeed(Const.GAME_SPEED);
        game.setTitle("Meteors!");
        game.run();
    }
}