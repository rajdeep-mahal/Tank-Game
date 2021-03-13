package TankGame;

import java.awt.*;

public class GameOverMenu {
    public void draw(Graphics grph) {
        Font f = new Font("arial", Font.BOLD, 50);
        grph.setColor(Color.WHITE);
        grph.setFont(f);
        grph.drawString("RESTART", 350, 470);        //380, 470
        grph.drawString("EXIT", 395, 560);          //395  , 560
        grph.setColor(Color.white);
        grph.drawRoundRect(320, 416, 290, 70, 20, 20);  // 360, 416
        grph.drawRoundRect(360, 416 + 90, 210, 70, 20, 20); // 360, 416
    }
}
