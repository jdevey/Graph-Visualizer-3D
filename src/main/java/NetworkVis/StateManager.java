package NetworkVis;

import java.io.IOException;
import java.util.ArrayList;

public class StateManager {

    private UserState userState;
    private GraphState graphState;
    private ArrayList<Observer> observers;

    StateManager(UserState userState, GraphState graphState) {
        this.userState = userState;
        this.graphState = graphState;
        observers = new ArrayList<>();
    }


    public void updateObservers() throws IOException {
        for(Observer observer: observers) {
            observer.update(this);
        }
    }

    void addObserver(Observer observer) {
        observers.add(observer);
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public GraphState getGraphState() {
        return graphState;
    }

    public void setGraphState(GraphState graphState) {
        this.graphState = graphState;
    }
}
