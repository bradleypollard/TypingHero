package game;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.FileParser;
  
public class GamePlayState extends BasicGameState {
  
    int stateID = -1;
    Map<Integer, Character> keys = new LinkedHashMap<Integer, Character>();
    Random r = new Random();
    Music song;
    float bpm; float beat; boolean hasBeat;
    private int keyHit = 30;
    private int keyMissed = 30;
    private int beatCount;
    public static final Set<Character> top = new HashSet<Character>();
    public static final Set<Character> mid = new HashSet<Character>();
    public static final Set<Character> bot = new HashSet<Character>();
    {
        for (int i = 16; i < 26; i++) {
            top.add(Input.getKeyName(i).charAt(0));
        }
        for (int i = 30; i < 39; i++) {
            mid.add(Input.getKeyName(i).charAt(0));
        }
        for (int i = 44; i < 51; i++) {
            bot.add(Input.getKeyName(i).charAt(0));   
        }
    }
    
  
    GamePlayState( int stateID ) 
    {
       this.stateID = stateID;
    }
  
    @Override
    public int getID() {
        return stateID;
    }
  
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
//        randomKeyGen(500);
        fileKeyGen("data/vortex.txt");
        bpm = 130;
        beat = 60/bpm;
        beatCount = 0;
        hasBeat = false;
        song = new Music("data/Vortex.ogg");
        song.play();
    }
  
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.gray);  // draw keyboard lines at bottom
        g.drawLine(285, 410, 375, 410); 
        g.drawLine(290, 430, 370, 430);
        g.drawLine(305, 450, 355, 450);

        g.drawLine(330, 390, 330, 470);
        
        if (keyHit < 20) {
            keyHit++;
            g.setColor(Color.green);
        } else if (keyMissed < 20) {
            keyMissed++;
            g.setColor(Color.red);
        } else {    
            g.setColor(Color.white);
        }
        
        
        for (int i = beatCount - 7; i < beatCount + 7; i ++) {
//            g.setColor(g.getColor().darker());
            if (keys.get(i) != null) {
                int y = 400;
                if (top.contains(keys.get(i))) {
                    y = 400;
                } else if (mid.contains(keys.get(i))) {
                    y = 420;
                } else {
                    y = 440;
                }
                g.drawString("" + keys.get(i), 325 + ((i - beatCount) * 25), y);
            }
        }
    }
  
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        
        float currentTime = song.getPosition();
        
        float mostRecentBeat = currentTime/beat;
        
        if (approxEquals(mostRecentBeat, Math.round(mostRecentBeat), 0.1f) && !hasBeat) {
//            System.out.println("YE: " + Math.round(mostRecentBeat) + " "  + mostRecentBeat);
            hasBeat = true;
            beatCount++;
        } else if (!approxEquals(mostRecentBeat, Math.round(mostRecentBeat), 0.1f)) {
            hasBeat = false;
        }
        
        // key checks
        Input in = gc.getInput();
        
        // row 1
        for (int i = 16; i < 26; i++) {
            keyCheck(in, i, currentTime);
        }
        // row 2
        for (int i = 30; i < 39; i++) {
            keyCheck(in, i, currentTime);
        }
        // row 3 
        for (int i = 44; i < 51; i++) {
            keyCheck(in, i, currentTime);
        }
        
    }
    
    private void keyCheck(Input in, int i, float currentTime) {
        if (in.isKeyPressed(i)) {
            System.out.println(Input.getKeyName(i));
            float mostRecentBeat = (currentTime + beat/2)/beat;
            if (Input.getKeyName(i).equals("" + keys.get(beatCount)) && 
                    approxEquals(mostRecentBeat, Math.round(mostRecentBeat), 0.75f*beat) ) {
                keyHit = 0;
            } else {
                keyMissed = 0;
            }
        } 
    }
  
    private void randomKeyGen(int k) {
        for (int i = 0; i < k; i++) {
            keys.put(r.nextInt(1000), (char)(r.nextInt(26) + 65));
        }
    }
    
    private void fileKeyGen(String URL) {
        keys = FileParser.parse(URL);
    }
    
    private boolean approxEquals(float i, float j, float d) {
        return (i > (j - d) && i < (j + d));
    }
}
