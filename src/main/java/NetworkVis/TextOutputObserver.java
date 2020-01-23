package NetworkVis;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TextOutputObserver extends Observer {
    private PrintWriter writer;
    private int updateCnt = 0;
    private int writeCnt = 0;
    private int updateFrequency; // Number of frames to wait between updates

    TextOutputObserver(PrintWriter writer, int updateFrequency) {
        this.writer = writer;
        this.updateFrequency = updateFrequency;
        writer.println("[");
    }

    public void update(StateManager stateManager) {
        ArrayList<Node> nodes = stateManager.getGraphState().getNodes();
        if (updateCnt++ % updateFrequency == 0) { // Only update every updateFrequency frames
            if (writeCnt > 0) {
                writer.print(",\n");
            }
            writer.println("  [");
            for (int i = 0; i < nodes.size(); ++i) {
                writer.print(nodes.get(i).serialize(i == 0));
            }
            ++writeCnt;
            writer.print("\n  ]");
        }
    }

    void close() {
        writer.print("\n]");
        writer.close();
    }
}
