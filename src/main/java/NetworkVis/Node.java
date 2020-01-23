package NetworkVis;

import java.awt.*;

class Node {
    private static int idCounter = 0;
    private int id;
    private Double x;
    private Double y;
    private Double z;
    private Double vx;
    private Double vy;
    private Double vz;
    private String label;
    private Color color;

    Node(Double x, Double y, Double z, String label) {
        this.id = idCounter++;
        this.x = x;
        this.y = y;
        this.z = z;
        vx = 0.0;
        vy = 0.0;
        vz = 0.0;
        this.label = label;
        this.color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
    }

    String serialize(boolean leading) {
        return  (leading ? "" : ",\n") +
                "    {" + "\n" +
                "      \"label\": \"" + this.getLabel() + "\",\n" +
                "      \"id\": " + this.getId() + ",\n" +
                "      \"x\": " + this.getX() + ",\n" +
                "      \"y\": " + this.getY() + ",\n" +
                "      \"z\": " + this.getZ() + ",\n" +
                "      \"vx\": " + this.getVx() + ",\n" +
                "      \"vy\": " + this.getVy() + "\n" +
                "      \"vz\": " + this.getVz() + "\n" +
                "    }";
    }

    Double getX() {
        return x;
    }

    void setX(Double x) {
        this.x = x;
    }

    Double getY() {
        return y;
    }

    void setY(Double y) {
        this.y = y;
    }

    Double getZ() {
        return z;
    }

    void setZ(Double z) {
        this.z = z;
    }

    Double getVx() {
        return vx;
    }

    void setVx(Double vx) {
        this.vx = vx;
    }

    Double getVy() {
        return vy;
    }

    void setVy(Double vy) {
        this.vy = vy;
    }

    Double getVz() {
        return vz;
    }

    void setVz(Double vz) {
        this.vz = vz;
    }

    private int getId() {
        return id;
    }

    String getLabel() {
        return label;
    }

    Color getColor() {
        return color;
    }

}
