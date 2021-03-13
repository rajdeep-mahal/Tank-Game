package TankGame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Bullet extends GameObject {
    private int iterations_collided = 0;
    private boolean small_Explosion = true;
    private String bullet_Owner;
    private boolean isInActive = false;
    boolean collided = false;
    static private BufferedImage bullet_img;
    static private BufferedImage explosion_img;
    static private BufferedImage big_Explosion_img;

    String getOwner() {
        return this.bullet_Owner;
    }

    public void setOwner(String owner) {
        this.bullet_Owner = owner;
    }

    public boolean getIsInactive() {
        return this.isInActive;
    }

    public void setIsInactive(boolean val) {
        this.isInActive = val;
    }

    public static void setBufferedImage(BufferedImage img) {
        bullet_img = img;
    }

    public static void setExplosionImage(BufferedImage exp) {
        explosion_img = exp;
    }

    public static void setBig_explosion_img(BufferedImage e){
        big_Explosion_img = e;
    }

    public void setSmall_explosion(boolean val){
        this.small_Explosion = val;
    }
    public boolean getSmall_explosion(){
        return this.small_Explosion;
    }

    Bullet(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.vx = (int) Math.round(3 * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(3 * Math.sin(Math.toRadians(angle)));
        this.angle = angle;
        this.my_rectangle = new Rectangle(x, y, bullet_img.getWidth(), bullet_img.getHeight());
    }

    public void update(){
        if (!collided){
            this.x = x + vx;
            this.y = y + vy;
            this.checkBorder();
        }
        else{
            iterations_collided++;
        }
        this.my_rectangle.setLocation(x,y);
    }


    public void drawImage(Graphics2D g2d) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), bullet_img.getWidth() / 2.0, bullet_img.getHeight() / 2.0);

        if (collided && small_Explosion) {

            g2d.drawImage(explosion_img, rotation, null);

            if (iterations_collided >= 2) {
                this.isInActive = true;
            }

        }else if(collided && !small_Explosion){
            g2d.drawImage(big_Explosion_img, rotation, null);

            if (iterations_collided >= 2) {
                this.isInActive = true;
            }
        } else {
            g2d.drawImage(bullet_img, rotation, null);
        }
    }

    public void collision(){
        collided = true;
    }

    private void checkBorder(){
        int left_limit = 30;
        if (x < left_limit){
            this.isInActive = true;
        }
        int right_limit = GameWorld.WORLD_WIDTH - 65;
        if (x >= right_limit){
            this.isInActive = true;
        }
        int lower_limit = 40;
        if (y < lower_limit){
            this.isInActive = true;
        }
        int upper_limit = GameWorld.WORLD_HEIGHT - 60;
        if (y >= upper_limit){
            this.isInActive = true;
        }
    }
}