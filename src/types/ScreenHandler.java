package types;

import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;


public abstract class ScreenHandler {
    public static ArrayList<Action> actions = new ArrayList<Action>();
    protected Robot robot;
    protected int windowID;

    public void execute() throws InterruptedException, IOException {
        System.out.println("Undefined script action execute! Please define execute() for ScreenHandlers.");
    };
    
    public static Action getCurrentAction() {
        return actions.get(0);
    }
    
    public static void addActionToFront(Action action ) {
        System.out.println("Adding " + action.name() + " to the front of the action list.");
        actions.add(0, action);
    }

    public ScreenHandler(Robot robot, int windowID) {
        super();
        this.robot = robot;
        this.windowID = windowID;
    }
    
    
    
}
