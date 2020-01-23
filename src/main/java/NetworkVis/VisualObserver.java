package NetworkVis;

import java.util.ArrayList;

public class VisualObserver extends Observer {

    private GraphCanvas graphCanvas;

    VisualObserver(GraphCanvas graphCanvas) {
        this.graphCanvas = graphCanvas;
    }

    @Override
    public void update(StateManager stateManager) {
        graphCanvas.render(stateManager);
    }
}
