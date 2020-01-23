package NetworkVis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    private static void printHelp() {
        System.out.println("You must specify a gml input file for the first argument, " +
                "and you may optionally specify an output json file for the second argument. Examples:\n" +
                "java Main karate.gml karate.json\n" +
                "java Main stateBorders.gml");
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            printHelp();
            return;
        }

        UserState userState = new UserState(0, 0, 0, Math.PI / 4, Math.PI / 4);
        //UserState userState = new UserState(0, 0, 0, 0, 0);

        Parser parser = new GMLParser();
        GraphState graph;

        try {
            graph = parser.parse(args[0]);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        StateManager stateManager = new StateManager(userState, graph);

        // Create our canvas
        GraphCanvas graphCanvas = new GraphCanvas(stateManager);
        graphCanvas.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        graphCanvas.setFocusable(true);
        graphCanvas.requestFocus();

        // Create handlers
        KeyboardHandler keyboardHandler = new KeyboardHandler(userState);
        graphCanvas.addKeyListener(keyboardHandler);
        MouseHandler mouseHandler = new MouseHandler(userState);
        graphCanvas.addMouseMotionListener(mouseHandler);
        graphCanvas.addMouseListener(mouseHandler);

        // Create the main window
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setContentPane(graphCanvas);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // From https://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        frame.getContentPane().setCursor(blankCursor);

        // Create and add observers
        VisualObserver display = new VisualObserver(graphCanvas);
        stateManager.addObserver(display);

        TextOutputObserver stateSerializer = null;
        if (args.length > 1) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(args[1]);
            }
            catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
            stateSerializer = new TextOutputObserver(writer, (int)Constants.FPS * 3);
            stateManager.addObserver(stateSerializer);
        }

        // Start engine
        Engine physicsEngine = new Engine();

        try {
            physicsEngine.run(stateManager/*, keyboardHandler*/);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Finish serializing and close the window
        if (stateSerializer != null) {
            stateSerializer.close();
        }
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
