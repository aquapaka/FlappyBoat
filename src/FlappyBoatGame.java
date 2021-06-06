
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aqua.tamlong
 */
public class FlappyBoatGame extends JFrame {
    final double MAX_FPS = 80;
    final String GAME_TITLE = "Flappy Boat";
    final String GAME_VERSION = "v0.1.0";
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = 720;
    final String FONT_PATH = "src/font/VT323-Regular.ttf";
    Font font;
    PlayScreen ps;
    
    public FlappyBoatGame(){
        loadFont(FONT_PATH);
        createScreen();
    }
    
    private void createScreen() {
        // Setup frame properties
        setTitle(GAME_TITLE+" "+GAME_VERSION);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));
        setVisible(true);
        setResizable(false);

        ps = new PlayScreen(this);
        add(ps);
        
        validate();
    }
    
    void restart() {       
        remove(ps);
        ps = new PlayScreen(this);
        add(ps);
        validate();
        System.out.println("Game Restarted");
    }
    
    private void loadFont(String path) {
        try {
            File font_file = new File(path);
            font = Font.createFont(Font.TRUETYPE_FONT, font_file);
        } catch (FontFormatException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public static void main(String arg[]) {
        FlappyBoatGame game = new FlappyBoatGame();
    }
}
