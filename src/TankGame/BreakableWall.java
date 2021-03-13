package TankGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    private boolean dead = false;
    private static BufferedImage breakable_wall_img;
    private int health = 100;

    BreakableWall(int x, int y){
        this.x = x;
        this.y = y;
        this.my_rectangle = new Rectangle(x, y, breakable_wall_img.getWidth(), breakable_wall_img.getHeight());
    }

    private void removeHealth(int val){
        if (health - val < 0){
            health = 0;
            dead = true;
        }
        else{
            health -= val;
        }
    }

    public void drawImage(Graphics2D g2d) {
        if (!dead){
            g2d.drawImage(breakable_wall_img, x, y, null);
        }
    }

    int getHealth(){
        return this.health;
    }

    static void set_breakable_wall_img(BufferedImage image){
        BreakableWall.breakable_wall_img = image;
    }

    public void collision(){
        this.removeHealth(50);
    }

    public void update(){
    }

    private void addHealth(int val){
        if (health + val > 100) {
            health = 100;
        }
        else{
            health += val;
        }
    }

    boolean isDead(){
        return dead;
    }

    void setDead(boolean dead){
        this.dead = dead;
    }
}