package NetworkVis;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class UserState {

    private double x;
    private double y;
    private double z;
    private double phi;
    private double theta;
    private Set<Integer> pressed = new HashSet<>();

    UserState(double x, double y, double z, double phi, double theta) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.phi = phi;
        this.theta = theta;
    }

    void incrementPosition() {
        if (isKeyPressed(KeyEvent.VK_W)) {
            y += Math.sin(theta) * Constants.MOVE_INCREMENT;
            double planar = Math.cos(theta);
            x += Math.cos(phi) * planar * Constants.MOVE_INCREMENT;
            z += Math.sin(phi) * planar * Constants.MOVE_INCREMENT;
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            y += Math.sin(theta) * Constants.MOVE_INCREMENT;
            double planar = Math.cos(theta);
            x += Math.cos(phi + Math.PI) * planar * Constants.MOVE_INCREMENT;
            z += Math.sin(phi + Math.PI) * planar * Constants.MOVE_INCREMENT;
        }
        if (isKeyPressed(KeyEvent.VK_A)) {
            y += Math.sin(theta) * Constants.MOVE_INCREMENT;
            double planar = Math.cos(theta);
            x += Math.cos(phi - Math.PI / 2) * planar * Constants.MOVE_INCREMENT;
            z += Math.sin(phi - Math.PI / 2) * planar * Constants.MOVE_INCREMENT;
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            y += Math.sin(theta) * Constants.MOVE_INCREMENT;
            double planar = Math.cos(theta);
            x += Math.cos(phi + Math.PI / 2) * planar * Constants.MOVE_INCREMENT;
            z += Math.sin(phi + Math.PI / 2) * planar * Constants.MOVE_INCREMENT;
        }
        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            y += Math.sin(theta - Math.PI / 2) * Constants.MOVE_INCREMENT;
            double planar = Math.cos(theta - Math.PI / 2);
            x += Math.cos(phi) * planar * Constants.MOVE_INCREMENT;
            z += Math.sin(phi) * planar * Constants.MOVE_INCREMENT;
        }
        if (isKeyPressed(KeyEvent.VK_SHIFT) || isKeyPressed(KeyEvent.VK_CONTROL)) {
            y += Math.sin(theta + Math.PI / 2) * Constants.MOVE_INCREMENT;
            double planar = Math.cos(theta + Math.PI / 2);
            x += Math.cos(phi) * planar * Constants.MOVE_INCREMENT;
            z += Math.sin(phi) * planar * Constants.MOVE_INCREMENT;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public void addKeyPressed(int code) {
        pressed.add(code);
    }

    public void removeKeyPressed(int code) {
        pressed.remove(code);
    }

    public boolean isKeyPressed(int code) {
        return pressed.contains(code);
    }
}
