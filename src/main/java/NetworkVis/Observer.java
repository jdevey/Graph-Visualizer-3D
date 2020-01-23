package NetworkVis;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Observer {
    public abstract void update(StateManager stateManager) throws IOException;
}
