package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TypingHero extends StateBasedGame {
    
    // int representations for the game states.
    public static final int MAINMENUSTATE = 0;
    public static final int GAMEPLAYSTATE = 1;
  
    public TypingHero()
    {
        super("TypingHero");
    }
  
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new TypingHero());
  
         app.setDisplayMode(640, 480, false);
         app.setTargetFrameRate(60);
         app.start();
    }
  
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GamePlayState(GAMEPLAYSTATE));
    }
}
