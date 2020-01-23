package NetworkVis;

import java.awt.event.*;

public class KeyboardHandler implements KeyListener {

    private UserState userState;

    KeyboardHandler(UserState userState) {
        this.userState = userState;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        userState.addKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        userState.removeKeyPressed(e.getKeyCode());
    }
}
