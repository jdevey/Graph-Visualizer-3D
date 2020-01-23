package NetworkVis;

import java.awt.event.KeyEvent;
import java.io.IOException;

import static NetworkVis.Constants.MIN_SLEEP_MS;

class Engine {
    void run(StateManager stateManager/*, KeyboardHandler escapeAction*/) throws IOException {
        long lastTime = System.currentTimeMillis();
        while (!stateManager.getUserState().isKeyPressed((char)KeyEvent.VK_ESCAPE)) {
            stateManager.getGraphState().updateNodes();
            stateManager.getUserState().incrementPosition();
            stateManager.updateObservers();
            long currentTime = System.currentTimeMillis();
            long dt = currentTime - lastTime;
            long timeToSleep = (long)Constants.TARGET - dt;
            lastTime = currentTime;
            if (timeToSleep > MIN_SLEEP_MS) {
                try {
                    Thread.sleep(timeToSleep);
                }
                catch(InterruptedException ignored) {
                }
            }
        }
    }
}
