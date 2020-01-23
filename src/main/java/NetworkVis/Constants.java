package NetworkVis;

import java.awt.*;

class Constants {
    // Physics constants
    static final double FPS = 30.0;
    static final double TARGET = 1000 / FPS;
    static final double SPRING_CONST = 0.001;
    static final double MAGNET_CONST = 32000.0;
    static final double GRAVITY_CONST = 0.002;
    static final double DAMPING_CONST = 0.5;
    static final int MIN_SLEEP_MS = 10;
    static final double SPEED_LMT_START = 100.0;
    static final double RUN_TIME = 10;

    // Display constants
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static final int SCREEN_WIDTH = screenSize.width;
    static final int SCREEN_HEIGHT = screenSize.height;
    static final int SCREEN_DEPTH = screenSize.width;
    static final int CENTER_X = screenSize.width / 2;
    static final int CENTER_Y = screenSize.height / 2;
    static final int CENTER_Z = screenSize.width / 2;
    static final int VISUAL_MULTIPLIER = 1000;
    static final int NODE_DIAMETER = 45 * VISUAL_MULTIPLIER;
    static final int EDGE_WIDTH = 3 * VISUAL_MULTIPLIER;
    static final int EDGE_BORDER_WIDTH = 0;
    static final int DEFAULT_FONT_SIZE = 36;

    // User control constants
    static final double FOV_WIDTH = Math.PI / 2; // 90 degrees
    static final double FOV_HEIGHT = FOV_WIDTH * ((double)SCREEN_HEIGHT / (double)SCREEN_WIDTH);
    static final double SENSITIVITY = 0.001;
    static final int MOVE_INCREMENT = 5;
}
