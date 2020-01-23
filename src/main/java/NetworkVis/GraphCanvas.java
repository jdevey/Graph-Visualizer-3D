package NetworkVis;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GraphCanvas extends JPanel implements KeyListener {
    // It's not ideal to store state information in here, but there isn't
    // another way. There is no way to pass extra parameter's into JPanel's
    // paint function, so state must be stored as member variables.
    private ArrayList<Node> nodes;
    private ArrayList<ArrayList<Integer>> adjList;
    private UserState userState;
    private ArrayList <Pair<Double, Double>> drawPositions;

    // Number of nodes in graph
    GraphCanvas(StateManager stateManager) {
        nodes = stateManager.getGraphState().getNodes();
        adjList = stateManager.getGraphState().getAdjList();
        userState = stateManager.getUserState();
        drawPositions = new ArrayList<>(Collections.nCopies(nodes.size(), new Pair<>(0.0, 0.0)));
    }

    private void updateDrawPositions() {
        for (int i = 0; i < nodes.size(); ++i) {
            double phi = Math.atan2(nodes.get(i).getZ() - userState.getZ(), nodes.get(i).getX() - userState.getX());

            double dp = Utils.pythagorean(userState.getX(), userState.getZ(), nodes.get(i).getX(), nodes.get(i).getZ());
            double theta = Math.atan2(nodes.get(i).getY() - userState.getY(), dp);

            drawPositions.set(i, new Pair <> (
                    (phi - userState.getPhi() + Constants.FOV_WIDTH / 2) * Constants.SCREEN_WIDTH, // x
                    (theta - userState.getTheta() + Constants.FOV_HEIGHT / 2) * Constants.SCREEN_HEIGHT)); // y
        }
    }

    private ArrayList <Pair <Double, Integer>> generateOrdering() {
        ArrayList <Pair <Double, Integer>> ordering = new ArrayList<>();
        for (int i = 0; i < nodes.size(); ++i) {
            Node node = nodes.get(i);
            double dn = Utils.pythagorean3D(
                    userState.getX(), userState.getY(), userState.getZ(),
                    node.getX(), node.getY(), node.getZ());
            ordering.add(new Pair <> (dn, i));
        }
        ordering.sort(Comparator.comparing(p -> -p.getKey())); // sort in descending order
        return ordering;
    }

    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        updateDrawPositions();

        ArrayList <Pair <Double, Integer>> ordering = generateOrdering();

        // Draw edges
        for (int order_ind = 0; order_ind < drawPositions.size(); ++order_ind) {
            int i = ordering.get(order_ind).getValue();
            ArrayList <Integer> adj = adjList.get(i);
            Node node = nodes.get(i);
            Pair <Double, Double> a = drawPositions.get(i);
            double dn = Utils.pythagorean3D(
                    userState.getX(), userState.getY(), userState.getZ(),
                    node.getX(), node.getY(), node.getZ());
            for (Integer adjacent : adj) {
                Node other = nodes.get(adjacent);
                Pair<Double, Double> b = drawPositions.get(adjacent);
                double on = Utils.pythagorean3D(
                        userState.getX(), userState.getY(), userState.getZ(),
                        other.getX(), other.getY(), other.getZ());

                int avgDist = (int) Math.max((dn + on) / 2, 1.0);

                g.setColor(Color.white);
                g.setStroke(new BasicStroke(Constants.EDGE_WIDTH / avgDist));
                g.drawLine((int) (double) a.getKey(), (int) (double) a.getValue(), (int) (double) b.getKey(), (int) (double) b.getValue());

                GradientPaint gradient = new GradientPaint(
                        (int) (double) a.getKey(), (int) (double) a.getValue(), node.getColor(),
                        (int) (double) b.getKey(), (int) (double) b.getValue(), other.getColor());
                g.setPaint(gradient);
                g.setStroke(new BasicStroke((Constants.EDGE_WIDTH - Constants.EDGE_BORDER_WIDTH) / avgDist));
                g.drawLine((int) (double) a.getKey(), (int) (double) a.getValue(), (int) (double) b.getKey(), (int) (double) b.getValue());
            }
        }

        // Draw nodes
        for (int order_ind = 0; order_ind < drawPositions.size(); ++order_ind) {
            int i = ordering.get(order_ind).getValue();
            Pair <Double, Double> a = drawPositions.get(i);
            Node node = nodes.get(i);
            int dn = (int)Math.max(Utils.pythagorean3D(
                    userState.getX(), userState.getY(), userState.getZ(),
                    node.getX(), node.getY(), node.getZ()), 1);

            Color centerColor = Utils.lightenColor(node.getColor(), 0.9f);
            float[] stopArr = {0.0f, 1.0f};
            Color[] colors = {centerColor, node.getColor()};
            RadialGradientPaint gp = new RadialGradientPaint((float)(double)a.getKey(), (float)(double)a.getValue(),
                    (float)(Constants.NODE_DIAMETER / dn), stopArr, colors);

            g.setPaint(gp);
            g.fillOval((int)Math.round(a.getKey()) - Constants.NODE_DIAMETER / 2 / dn,
                    (int)Math.round(a.getValue()) - Constants.NODE_DIAMETER / 2 / dn,
                    Constants.NODE_DIAMETER / dn, Constants.NODE_DIAMETER / dn);
        }

        // Draw labels
        for (int order_ind = 0; order_ind < drawPositions.size(); ++order_ind) {
            int i = ordering.get(order_ind).getValue();
            Pair <Double, Double> a = drawPositions.get(i);
            Node node = nodes.get(i);
            int dn = (int)Math.max(Utils.pythagorean3D(
                    userState.getX(), userState.getY(), userState.getZ(),
                    node.getX(), node.getY(), node.getZ()), 1);
            Font font = new Font("TimesRoman", Font.BOLD,
                    Constants.DEFAULT_FONT_SIZE * Constants.VISUAL_MULTIPLIER / dn);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setFont(font);
            double x = a.getKey() + (double)Constants.NODE_DIAMETER / 2 / dn + 2;
            double y = a.getValue() - fontMetrics.getHeight() / 2 + fontMetrics.getAscent() - 1;
            g.setPaint(Color.black);
            g.drawString(node.getLabel(), (int)x, (int)y);
            g.setPaint(Color.white);
            g.drawString(node.getLabel(), (int)x - 1, (int)y - 1);
        }

        // Draw cursor
        g.setPaint(Color.white);
        g.setStroke(new BasicStroke(4));
        g.drawLine(Constants.SCREEN_WIDTH / 2 - 10, Constants.SCREEN_HEIGHT / 2,
                Constants.SCREEN_WIDTH / 2 + 10, Constants.SCREEN_HEIGHT / 2);
        g.drawLine(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 - 10,
                Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + 10);
    }

    void render(StateManager stateManager) {
        this.nodes = stateManager.getGraphState().getNodes();
        this.adjList = stateManager.getGraphState().getAdjList();
        this.userState = stateManager.getUserState();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getKeyChar());
    }
}
