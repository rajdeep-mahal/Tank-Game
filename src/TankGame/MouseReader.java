package TankGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseReader implements MouseListener {


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouse_x_coord = e.getX();
        int mouse_y_coord = e.getY();

        if (GameWorld.state == GameWorld.Game_State.menu)
        {
            if (mouse_x_coord >= 360 && mouse_x_coord <= 360 + 210) {
                if (mouse_y_coord >= 416 && mouse_y_coord <= 416 + 70) {
                    GameWorld.state = GameWorld.Game_State.game;
                } else if (mouse_y_coord >= 416 + 90 && mouse_y_coord <= 416 + 90 + 70) {
                    GameWorld.state = GameWorld.Game_State.help;
                } else if (mouse_y_coord >= 416 + 90 + 90 && mouse_y_coord <= 416 + 90 + 90 + 70) {
                    GameWorld.state = GameWorld.Game_State.exit;
                }
            }
        } else if (GameWorld.state == GameWorld.Game_State.help) {
            //this block of code, serves to handle the pressing of the "return to main menu" button on the help screen
            if (mouse_x_coord >= 15 && mouse_x_coord <= 308) {
                if (mouse_y_coord >= 553 && mouse_y_coord <= 659) {
                    GameWorld.state = GameWorld.Game_State.menu;
                }
            }
        } else if (GameWorld.state == GameWorld.Game_State.over) {
            if (mouse_x_coord >= 320 && mouse_x_coord <= 320 + 290 && mouse_y_coord >= 416 && mouse_y_coord <= 416 + 70) {
                GameWorld.state = GameWorld.Game_State.restart;
            } else if (mouse_x_coord >= 360 && mouse_x_coord <= 360 + 210 && mouse_y_coord >= 416 + 90 && mouse_y_coord <= 416 + 90 + 70) {
                GameWorld.state = GameWorld.Game_State.exit;
            }
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
}
