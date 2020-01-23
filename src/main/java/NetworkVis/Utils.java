package NetworkVis;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

class Utils {
    static Double distance(Double a, Double b) {
        return b - a;
    }

    static double pythagorean(double x1, double y1, double x2, double y2) {
        return Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    static Double pythagorean3D(Double a, Double b, Double c) {
        return Math.sqrt( Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2));
    }

    static double pythagorean3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }

    static double pythagorean3D(Node a, Node b) {
        return Math.sqrt( Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }

    static Double calculateSpringForce(Double distance, Double springConstant) {
        return distance * springConstant;
    }

    static Double calculateMagneticForce(Double numeratorConstants, Double distance) {
        return numeratorConstants / (Math.pow(distance, 2));
    }

    static ArrayList <Double> makeUnit(double a, double b, double c) {
        double total = pythagorean3D(a, b, c);
        return new ArrayList <>(Arrays.asList(a / total, b / total, c / total));
    }

    // lighten a color by percentWhite
    public static Color lightenColor(Color c, float percentWhite) {
        float inv = 1 - percentWhite;
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        return new Color(
                (int)(r * inv + 255 * percentWhite),
                (int)(g * inv + 255 * percentWhite),
                (int)(b * inv + 255 * percentWhite));
    }
}
