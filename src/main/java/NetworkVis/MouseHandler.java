package NetworkVis;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class MouseHandler extends MouseInputAdapter {

    private UserState userState;
    private Robot robot = null;

    MouseHandler(UserState userState) {
        this.userState = userState;
        try {
            robot = new Robot();
        }
        catch (AWTException awtExcept) {
            System.out.println(awtExcept.getMessage());
            return;
        }
        robot.mouseMove(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        userState.setPhi(userState.getPhi() + (e.getX() - (float)Constants.SCREEN_WIDTH / 2) * Constants.SENSITIVITY);
        userState.setTheta(userState.getTheta() + (e.getY() - (float)Constants.SCREEN_HEIGHT / 2) * Constants.SENSITIVITY);
        robot.mouseMove(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
}
