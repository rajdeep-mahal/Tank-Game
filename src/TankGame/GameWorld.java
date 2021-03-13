package TankGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;


public class GameWorld extends JPanel {

    //to get the screen, we cut the large WORLD image based on SCREEN_WIDTH and SCREEN_HEIGHT
    private static final int SCREEN_HEIGHT = 680;           //672
    private static final int SCREEN_WIDTH = 961;            //960
    static final int WORLD_HEIGHT = 1920; // 1920/32 = 60 walls high
    static final int WORLD_WIDTH = 1536;  // 1536/32 = 48 walls wide
    private final int Tank1_spawn_x = 200;
    private final int Tank1_spawn_y = 150;
    private final int Tank1_spawn_angle = 0;
    private final int Tank2_spawn_x = 1200;
    private final int Tank2_spawn_y = 1700;
    private final int Tank2_spawn_angle = 180;
    
    private BufferedImage world;
    private BufferedImage tank_img;
    private BufferedImage bullet_img;
    private BufferedImage background_img;
    private BufferedImage unbreakable_wall_img;
    private BufferedImage breakable_wall_img;
    private BufferedImage exp_img;
    private BufferedImage large_explosion_img;

    private Graphics2D buffer;
    private JFrame jf;
    private Tank t1;
    private Tank t2;
    //private static BufferedImage tank_img;
    private static BufferedImage player1_win_img;
    private static BufferedImage player2_win_img;
    private static BufferedImage menu_img;
    private static BufferedImage help_img;
    private boolean player1_won;
    private boolean player2_won;

    private CollisionHandler CH;
    static private Menu m;
    static private GameOverMenu overMenu;

    private ArrayList<GameObject> game_objects;
    private int Player1_num_lives;
    private int Player2_num_lives;

    static Game_State state;


    public void setGame_objects(ArrayList<GameObject> game_objects) {
        this.game_objects = game_objects;
    }


    void addGame_object(GameObject obj) {
        this.game_objects.add(obj);
    }


    enum Game_State {
        menu, game, over, restart, help, exit,
    }


    public static void main(String[] args) {
        Thread x;
        GameWorld TREX = new GameWorld();
        TREX.CH = new CollisionHandler();
        TREX.init();
        TREX.init_new_game();
        TREX.init_listeners();

        try {
            while (true) {
                TREX.repaint();

                if (GameWorld.state == Game_State.restart) {
                    TREX.init_new_game();
                    TREX.init_listeners();
                    GameWorld.state = Game_State.game;
                }

                if (GameWorld.state == Game_State.game) {
                    for (int i = 0; i < TREX.game_objects.size(); i++) {
                        if (TREX.game_objects.get(i) instanceof Bullet) {
                            if (((Bullet) TREX.game_objects.get(i)).getIsInactive()) { // is inactive
                                TREX.game_objects.remove(i);
                                i--;
                            } else {
                                TREX.game_objects.get(i).update();
                            }
                        }
                        if (TREX.game_objects.get(i) instanceof Tank) {
                            if (((Tank) TREX.game_objects.get(i)).getHealth() == 0) { //tank is destroyed
                                if ((((Tank) TREX.game_objects.get(i)).getTag()).equals("Tank1")) { //player 1 tank is destroyed
                                    if (TREX.Player1_num_lives > 1) { //game continues (player 1 has remaining lives)
                                        TREX.Player1_num_lives--;

                                        //respawning
                                        ((Tank) TREX.game_objects.get(i)).setHealth(100);  //replenishing health to full health
                                        TREX.game_objects.get(i).setX(TREX.Tank1_spawn_x);
                                        TREX.game_objects.get(i).setY(TREX.Tank1_spawn_y);
                                        TREX.game_objects.get(i).setAngle(TREX.Tank1_spawn_angle);


                                    } else { // player 2 has won
                                        TREX.Player1_num_lives = 0;
                                        TREX.player2_won = true;
                                        GameWorld.state = Game_State.over;
                                    }
                                }
                                if ((((Tank) TREX.game_objects.get(i)).getTag()).equals("Tank2")) { //player 2 tank is destroyed
                                    if (TREX.Player2_num_lives > 1) { //game continues (player 2 has remaining lives)
                                        TREX.Player2_num_lives--;


                                        ((Tank) TREX.game_objects.get(i)).setHealth(100);
                                        TREX.game_objects.get(i).setX(TREX.Tank2_spawn_x);
                                        TREX.game_objects.get(i).setY(TREX.Tank2_spawn_y);
                                        TREX.game_objects.get(i).setAngle(TREX.Tank2_spawn_angle);


                                    } else { //player 1 has won
                                        TREX.Player2_num_lives = 0;
                                        TREX.player1_won = true;
                                        GameWorld.state = Game_State.over;
                                    }
                                }

                            }
                        }
                        if (((TREX.game_objects.get(i) instanceof BreakableWall) && ((BreakableWall) TREX.game_objects.get(i)).getHealth() == 0)) {
                            TREX.game_objects.remove(i);
                        }
                    }

                    TREX.game_objects = TREX.CH.HandleCollisions(TREX.game_objects);  //handling collisions


                    TREX.t1.update();
                    TREX.t2.update();

                    Thread.sleep(1000 / 144);
                } else if (state == Game_State.exit) { //exiting
                    TREX.jf.dispose();
                    System.exit(0);
                }
            }
        } catch (InterruptedException ignored) {

        }
    }

    private void init_new_game() {
        player1_won = false;
        player2_won = false;
        game_objects = new ArrayList<>();
        Player1_num_lives = 2;
        Player2_num_lives = 2;
        state = Game_State.menu;

        //creating tanks
        t1 = new Tank(Tank1_spawn_x, Tank1_spawn_y, 0, 0, Tank1_spawn_angle, tank_img);
        t1.setTag("Tank1");
        t2 = new Tank(Tank2_spawn_x, Tank2_spawn_y, 0, 0, Tank2_spawn_angle, tank_img);
        t2.setTag("Tank2");

        m = new Menu(); //instantiating the menu
        overMenu = new GameOverMenu();

        //adding background
        for (int i = 0; i < WORLD_WIDTH; i = i + 320) {
            for (int j = 0; j < WORLD_HEIGHT; j = j + 240) {
                game_objects.add(new Wall(i, j, true)); // the true is denote its a background(allows for linking proper images)
            }
        }

        int[] new_map_array = { //width is 48, height is 60
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};


        int column = 0; //left to right(0->24)
        int entire_index = 0;

        for (int i = 0; i < 60; i++) { //loops up and down(entire horizontal row)

            for (int j = 0; j < 48; j++) {
                if (column == 60) { //resetting column
                    column = 0;
                }
                int temp_val = new_map_array[entire_index]; //holds value in array
                if (temp_val != 0) {
                    if (temp_val == 2) { // breakable wall
                        game_objects.add(new BreakableWall(j * 32, i * 32));
                    } else {
                        game_objects.add(new Wall(j * 32, i * 32, false));
                    }
                }
                column++;
                entire_index++;
            }
        }

        game_objects.add(t1); //adding tank 1
        t1.setGW(this);
        game_objects.add(t2); //adding tank 2
        t2.setGW(this);

        PowerUp power1 = new PowerUp(780, 750, true, false); //making a health PowerUp
        game_objects.add(power1);

        PowerUp power2 = new PowerUp(682, 750, true, false); //making a health PowerUp
        game_objects.add(power2);

        PowerUp power3 = new PowerUp(800, 200, true, false);
        game_objects.add(power3);

        PowerUp power4 = new PowerUp(200, 900, true, false);
        game_objects.add(power4);

        PowerUp power5 = new PowerUp(900, 900, true, false);
        game_objects.add(power5);

        PowerUp power6 = new PowerUp(730, 852, false, true);  //making a speed PowerUp
        game_objects.add(power6);

        PowerUp power7 = new PowerUp(450, 800, false, true);
        game_objects.add(power7);

        PowerUp power8 = new PowerUp(930, 1452, false, true);  //making a speed PowerUp
        game_objects.add(power8);

        PowerUp power9 = new PowerUp(1050, 290, false, true);
        game_objects.add(power9);
    }


    private void init_listeners() {
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q); //adding control
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);

        this.addMouseListener(new MouseReader());
    }


    private void init() {
        this.jf = new JFrame("The Ultimate Tank Game");
        this.world = new BufferedImage(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        //BufferedImage t1img = null, bullet_image, background_image, unbreakable_wall_img, breakable_wall_img, exp_img, large_explosion_img;

        try {
            tank_img = read(getClass().getResource("/tank1.png"));

            unbreakable_wall_img = read(getClass().getResource("/Wall2.gif"));
            Wall.set_unbreakable_wall_img(unbreakable_wall_img);

            background_img = read(getClass().getResource("/Background.bmp"));
            Wall.setBackground_img(background_img);  //Resource/Background.bmp

            breakable_wall_img = read(getClass().getResource("/Wall1.gif"));
            BreakableWall.set_breakable_wall_img(breakable_wall_img);

            bullet_img = read(getClass().getResource("/Weapon.gif"));
            Bullet.setBufferedImage(bullet_img); //setting the bullet image

            exp_img = read(getClass().getResource("/Explosion_small.gif"));
            Bullet.setExplosionImage(exp_img);

            large_explosion_img = read(getClass().getResource("/Explosion_large.gif"));
            Bullet.setBig_explosion_img(large_explosion_img);

            GameWorld.menu_img = read(getClass().getResource("/Menu_page.PNG"));
            GameWorld.help_img = read(getClass().getResource("/help.png"));
            // GameWorld.menu_img = ImageIO.read(getClass().getResource("/resources/Menu_img.png"));

            GameWorld.player1_win_img = read(getClass().getResource("/player1_wins.png"));
            GameWorld.player2_win_img = read(getClass().getResource("/player2_wins.png"));

            PowerUp.setHealth_img(read(getClass().getResource("/health-potion.png")));
            PowerUp.setSpeed_img(read(getClass().getResource("/run.png")));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this); //adding the GameWorld to the Jframe

        this.jf.setSize(GameWorld.SCREEN_WIDTH + 20, GameWorld.SCREEN_HEIGHT + 40);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);


        if (GameWorld.state == Game_State.menu) {       //menu state
            (g).drawImage(menu_img, 0, 0, SCREEN_WIDTH + 2, SCREEN_HEIGHT, null);
            m.drawImage(g);
        } else if (GameWorld.state == Game_State.help) {  //help state
            (g).drawImage(help_img, 0, 0, SCREEN_WIDTH + 2, SCREEN_HEIGHT, null);
        } else if (GameWorld.state == Game_State.game) {  //game state


            for (int i = 0; i < game_objects.size(); i++) {

                game_objects.get(i).drawImage(buffer);

            }

            //these 4 variables are not required per say, but make reading the code sections below easier
            int player1_x_Coord = t1.getX();
            int player2_x_Coord = t2.getX();
            int player1_y_Coord = t1.getY();
            int player2_y_Coord = t2.getY();


            if (player1_x_Coord < SCREEN_WIDTH / 4) {
                player1_x_Coord = SCREEN_WIDTH / 4;
            }
            if (player2_x_Coord < SCREEN_WIDTH / 4) {
                player2_x_Coord = SCREEN_WIDTH / 4;
            }
            if (player1_x_Coord > WORLD_WIDTH - SCREEN_WIDTH / 4) {
                player1_x_Coord = WORLD_WIDTH - SCREEN_WIDTH / 4;
            }
            if (player2_x_Coord > WORLD_WIDTH - SCREEN_WIDTH / 4) {
                player2_x_Coord = WORLD_WIDTH - SCREEN_WIDTH / 4;
            }
            if (player1_y_Coord < SCREEN_HEIGHT / 2) {
                player1_y_Coord = SCREEN_HEIGHT / 2;
            }
            if (player2_y_Coord < SCREEN_HEIGHT / 2) {
                player2_y_Coord = SCREEN_HEIGHT / 2;
            }
            if (player1_y_Coord > WORLD_HEIGHT - SCREEN_HEIGHT / 2) {
                player1_y_Coord = WORLD_HEIGHT - SCREEN_HEIGHT / 2;
            }
            if (player2_y_Coord > WORLD_HEIGHT - SCREEN_HEIGHT / 2) {
                player2_y_Coord = WORLD_HEIGHT - SCREEN_HEIGHT / 2;
            }


            BufferedImage left_split_screen = world.getSubimage(player1_x_Coord - SCREEN_WIDTH / 4, player1_y_Coord - SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, SCREEN_HEIGHT);
            BufferedImage right_split_screen = world.getSubimage(player2_x_Coord - SCREEN_WIDTH / 4, player2_y_Coord - SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, SCREEN_HEIGHT);

            g2.drawImage(left_split_screen, 0, 0, null);
            g2.drawImage(right_split_screen, SCREEN_WIDTH / 2 + 5, 0, null); //the +5 is to have a gap between the split screens

            g2.drawImage(world, SCREEN_WIDTH / 2 - GameWorld.WORLD_WIDTH / 6 / 2, SCREEN_HEIGHT - GameWorld.WORLD_HEIGHT / 6, GameWorld.WORLD_WIDTH / 6, WORLD_HEIGHT / 6, null);
            g2.setFont(new Font("SansSerif", Font.BOLD, 30));
            g2.setColor(Color.WHITE);
            g2.drawString("Player1 lives: " + this.Player1_num_lives, 10, 28);
            g2.drawString("Player2 lives: " + this.Player2_num_lives, SCREEN_WIDTH / 2 + 10, 28);


            g2.drawString("[", 10, 58);
            g2.drawString("[", SCREEN_WIDTH / 2 + 10, 58);
            g2.drawString("]", 230, 58);
            g2.drawString("]", SCREEN_WIDTH / 2 + 230, 58);
            g2.setColor(Color.green);


            g2.fillRect(25, 40, 2 * t1.getHealth(), 20);
            g2.fillRect(SCREEN_WIDTH / 2 + 25, 40, 2 * t2.getHealth(), 20);
        } else if (GameWorld.state == Game_State.over) {
            if (player1_won) {
                (g2).drawImage(player1_win_img, 0, 0, SCREEN_WIDTH + 10, SCREEN_HEIGHT, null);
                overMenu.draw(g2);
            }
            if (player2_won) {
                (g2).drawImage(player2_win_img, 0, 0, SCREEN_WIDTH + 10, SCREEN_HEIGHT, null);
                overMenu.draw(g2);
            }
        }
    }


}
