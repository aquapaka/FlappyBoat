


import java.awt.Color;
import java.awt.Font;
import model.Bullet;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import model.Boat;
import model.Enemy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aqua.tamlong
 */
public class PlayScreen extends JLayeredPane{
    private final String IMG_PATH = "/img/Background.png";
    private Image img;
    private ImageIcon icon;
    private final JLabel lbBackground = new JLabel();
    private final JLabel lbBackground2 = new JLabel();
    final int IMG_SCALE = 4;
    final int BOAT_INIT_X = 200;
    final int BOAT_INIT_Y = 500;
    final double ENEMY_SPAWN_TIME = 100;
    final double SPAWN_RATE = 1;
    double currentSpawnTime;
    double currentSpawnRate;
    FlappyBoatGame game;
    Boat boat;
    Timer timer;
    ArrayList<Bullet> bulletList = new ArrayList<>();
    ArrayList<Enemy> enemyList = new ArrayList<>();
    Random rd = new Random(System.currentTimeMillis());
    int randomLocX;
    int randomLocY;
    JLabel lbGameOver, lbTutorial, lbEndScore, lbScore;
    int score = 0;
    JButton btnRestart;
    boolean isTutorial = true;
    
    public PlayScreen(FlappyBoatGame game) {
        this.game = game;
        createScreen();
        runGameLoop();
    }
    
    private void createScreen() {
        setLayout(null);
        
        // Load and add background
        img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_PATH));
        icon = new ImageIcon(img);
        img = img.getScaledInstance(icon.getIconWidth()*IMG_SCALE, icon.getIconHeight()*IMG_SCALE, Image.SCALE_FAST);
        icon = new ImageIcon(img);
        lbBackground.setIcon(icon);
        lbBackground.setBounds(new Rectangle(icon.getIconWidth(), icon.getIconHeight()));
        add(lbBackground, new Integer(0));
        lbBackground2.setIcon(icon);
        lbBackground2.setBounds(icon.getIconWidth(), 0, icon.getIconWidth(), icon.getIconHeight());
        add(lbBackground2, new Integer(0));
        
        // Create and add Boat
        boat = new Boat(BOAT_INIT_X, BOAT_INIT_Y);
        add(boat, new Integer(1));
        
        // Allow boat to jump and fire when press SPACE
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(boat.isAlive() && boat.getLocY() > 0) {
                    if(isTutorial) {
                        isTutorial = false;
                        lbTutorial.setVisible(false);
                    }
                    boat.jump();
                    fire();
                }   
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        // Allow boat to jump and fire when right click
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(boat.isAlive() && boat.getLocY() > 0) {
                    if(isTutorial) {
                        isTutorial = false;
                        lbTutorial.setVisible(false);
                    }
                    boat.jump();
                    fire();
                }       
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        // Add score label to the game
        lbScore = createLabel("0", 48f, Color.white, 50);
        add(lbScore, new Integer(2));
        
        // Add tutorial label to the game
        lbTutorial = createLabel("Press SPACE or RIGHT CLICK to JUMP", 32f, Color.white, 300);
        add(lbTutorial, new Integer(2));
    }
    
    /**
     * This method create and add a moving bullet at player boat position
     */
    private void fire() {
        Bullet bl = new Bullet(boat.getLocX()+30, boat.getLocY()+20);
        add(bl, new Integer(1));
        bulletList.add(bl);
    }
    
    private void runGameLoop() {
        currentSpawnTime = ENEMY_SPAWN_TIME;
        currentSpawnRate = SPAWN_RATE;
        timer = new Timer((int)(1000/game.MAX_FPS), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isTutorial) {
                    // Increase spawn rate, make enemy spawn faster
                    currentSpawnRate += 0.001;

                    // Spawn new enemy after an amount of time
                    currentSpawnTime -= currentSpawnRate;
                    if (currentSpawnTime < 0) {
                        addNewEnemy();
                        currentSpawnTime = ENEMY_SPAWN_TIME;
                    }                    
                }               
                
                update();                
            }
        });       
        timer.start();
    }
    
    private void addNewEnemy() {
        // Generate random enemy location
        randomLocX = rd.nextInt((int)(game.SCREEN_WIDTH*1.5))+game.SCREEN_WIDTH;
        randomLocY = rd.nextInt(game.SCREEN_HEIGHT);
        
        // Add new enemy to the game
        Enemy e = new Enemy(randomLocX, randomLocY);
        add(e, new Integer(1));
        enemyList.add(e);        
    }
    
    /**
     * This method is called when the game is over
     */
    private void gameOver() {
        boat.setAlive(false);
        boat.setVelX(-10);
        
        // Create and add game over UI
        lbScore.setVisible(false);
        lbGameOver = createLabel("Game over", 80f, Color.white, 300);
        add(lbGameOver, new Integer(2));
        lbEndScore = createLabel("Score: "+score, 64f, Color.white, 500);
        add(lbEndScore, new Integer(2));
        btnRestart = createButton("Restart", 48f, Color.orange, 300);
        btnRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restart();
            }
        });
        add(btnRestart, new Integer(2));

    }
    
    private JLabel createLabel(String text, float size, Color color, int locY) {
        Font gameFont = game.font.deriveFont(size);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(gameFont);
        label.setForeground(color);
        label.setBounds(0, 0, game.SCREEN_WIDTH, locY);
        
        return label;
    }
    
    private JButton createButton(String text, float size, Color color, int locY) {
        Font gameFont = game.font.deriveFont(size);
        JButton btn = new JButton(text);
        btn.setFont(gameFont);
        btn.setForeground(color);
        btn.setBounds(game.SCREEN_WIDTH/2-150, locY, 300, 100);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        
        return btn;
    }
    
    /**
     * This method is use to increase game score
     * @param score amount of score need to add
     */
    private void addScore(int score) {
        this.score += score;
        lbScore.setText(this.score+"");
    }
    
    /**
     * This method update all component on the screen
     */
    private void update() {
        if(!isTutorial) boat.update();
        
        // Game over if player boat jump higher than screen height
        if(boat.getLocation().y < 0) {           
            gameOver();
        }
        // Game over if player boat is fall out of the map
        if (boat.isAlive() && boat.getLocation().y > game.SCREEN_HEIGHT) {
            gameOver();
        }
        
        // This block of code is use to make background run finifity
        if(lbBackground.getX()+lbBackground.getWidth() < 0) {
            lbBackground.setLocation(lbBackground2.getX()+lbBackground2.getWidth(), 0);
        }
        if(lbBackground2.getX()+lbBackground2.getWidth() < 0) {
            lbBackground2.setLocation(lbBackground.getX()+lbBackground.getWidth(), 0);
        }
        lbBackground.setLocation(lbBackground.getX()-3, 0);
        lbBackground2.setLocation(lbBackground2.getX()-3, 0);
        
        // Remove all dead object from array list
        bulletList.removeIf(bl -> bl.isAlive() == false);
        enemyList.removeIf(e -> e.isAlive() == false);
        
        for(Bullet bl : bulletList) {
            // Add score and, remove bullet and enemy if they are intersect
            for (Enemy e : enemyList) {
                if(bl.getBounds().intersects(e.getBounds())) {
                    e.setAlive(false);
                    bl.setAlive(false);
                    remove(e);
                    remove(bl);
                    addScore(125);
                }
            }
            
            // Remove bullet if it run out of screen
            if(bl.getLocX() > game.SCREEN_WIDTH) {
                bl.setAlive(false);
                remove(bl);
            }
            
            bl.update();
        }
        
        for(Enemy e : enemyList) {
            // Remove enemy if it run out of screen
            if(e.getLocX()+e.getWidth() < 0) {
                e.setAlive(false);
                remove(e);              
            }
            
            // Game over if player boat touch an enemy
            if(e.getBounds().intersects(boat.getBounds())) {
                gameOver();
            }
            
            e.update();
        }
        
        // Make all components be display properly
        repaint();
        // Play screen need to be focus for key listener to work
        requestFocus();
    }
}
