package NetworkVis;

import java.time.Instant;
import java.util.ArrayList;

import static NetworkVis.Constants.DAMPING_CONST;
import static NetworkVis.Utils.*;

class GraphState {
    private ArrayList<Node> nodes;
    private ArrayList <ArrayList <Integer>> adjList;
    private Instant simulationStart;

    GraphState(ArrayList<Node> nodes, ArrayList <ArrayList <Integer>> adjList) {
        this.nodes = nodes;
        this.adjList = adjList;
        simulationStart = Instant.now();
    }

    private long getSecondsSinceStart() {
        return Instant.now().getEpochSecond() - simulationStart.getEpochSecond();
    }

    private double getSpeedLimit() {
        long seconds = getSecondsSinceStart();
        return seconds > Constants.RUN_TIME ? 0.0 : Constants.SPEED_LMT_START / Math.pow(seconds, 19 / 10);
    }

    void updateNodes() {
        for(Node node: nodes) {
            node.setX(node.getX() + (node.getVx() * DAMPING_CONST));
            node.setY(node.getY() + (node.getVy() * DAMPING_CONST));
            node.setZ(node.getZ() + (node.getVz() * DAMPING_CONST));
        }

        for (int i = 0; i < nodes.size(); ++i) {
            Node node = nodes.get(i);
            Double linkForceX = 0.0;
            Double linkForceY = 0.0;
            Double linkForceZ = 0.0;
            Double magForceX = 0.0;
            Double magForceY = 0.0;
            Double magForceZ = 0.0;

            // calculate the force from the springs
            for (int other : adjList.get(i)) {
                Node link = nodes.get(other);
                linkForceX += Utils.calculateSpringForce(
                        Utils.distance(node.getX(), link.getX()),
                        Constants.SPRING_CONST);
                linkForceY += Utils.calculateSpringForce(
                        Utils.distance(node.getY(), link.getY()),
                        Constants.SPRING_CONST);
                linkForceZ += Utils.calculateSpringForce(
                        Utils.distance(node.getZ(), link.getZ()),
                        Constants.SPRING_CONST);
            }

            // calculate the force from the magnets
            for(Node other : nodes) {
                if (other.equals(node)) {
                    continue;
                }
                double distance = pythagorean3D(node, other);
                double magForce = Utils.calculateMagneticForce(Constants.MAGNET_CONST, distance);

                double dx = (node.getX() - other.getX());
                double dy = (node.getY() - other.getY());
                double dz = (node.getZ() - other.getZ());
                ArrayList <Double> unitVec = makeUnit(dx, dy, dz);

                magForceX += unitVec.get(0) * magForce;
                magForceY += unitVec.get(1) * magForce;
                magForceZ += unitVec.get(2) * magForce;
            }

            Double totalForceX = linkForceX + magForceX;
            Double totalForceY = linkForceY + magForceY;
            Double totalForceZ = linkForceZ + magForceZ;

            // The further the node is from the center, the more we pull it towards the center
            totalForceX += Constants.GRAVITY_CONST * Utils.distance(node.getX(), (double)Constants.CENTER_X);
            totalForceY += Constants.GRAVITY_CONST * Utils.distance(node.getY(), (double)Constants.CENTER_Y);
            totalForceZ += Constants.GRAVITY_CONST * Utils.distance(node.getZ(), (double)Constants.CENTER_Z);

            double speedLmt = getSpeedLimit();
            node.setVx(Math.max(-speedLmt, Math.min(speedLmt, node.getVx() + totalForceX)));
            node.setVy(Math.max(-speedLmt, Math.min(speedLmt, node.getVy() + totalForceY)));
            node.setVz(Math.max(-speedLmt, Math.min(speedLmt, node.getVz() + totalForceZ)));
        }
    }

    ArrayList<Node> getNodes() {
        return nodes;
    }

    ArrayList <ArrayList <Integer>> getAdjList() {
        return adjList;
    }
}
