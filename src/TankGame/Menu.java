package TankGame;


import java.awt.*;

public class Menu {
    public void drawImage(Graphics grph) {
        Font f = new Font("arial", Font.BOLD, 50);
        grph.setColor(Color.WHITE);
        grph.setFont(f);
        grph.drawString("START", 380, 470);        //380, 470
        grph.drawString("HELP", 395, 560);          //395  , 560
        grph.drawString("EXIT", 397, 650);          //397 , 650
        grph.setColor(Color.white);
        grph.drawRoundRect(360, 416, 210, 70, 20, 20);  // 360, 416
        grph.drawRoundRect(360, 416 + 90, 210, 70, 20, 20); // 360, 416
        grph.drawRoundRect(360, 416 + 90 + 90, 210, 70, 20, 20);  // 360, 416
    }
}

