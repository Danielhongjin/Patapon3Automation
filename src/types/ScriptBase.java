package types;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;

import backend.ScreenIdentifier;
import backend.WindowGrab.User32;

public class ScriptBase {
    private ArrayList<Action> actions = new ArrayList<Action>();
    private int iterations;
    
    public Action getCurrentAction() {
        return actions.get(0);
    }
    
    public void addActionToFront(Action action ) {
        System.out.println("Adding " + action.name() + " to the front of the action list.");
        actions.add(0, action);
    }
    
    public void addActionIndex(Action action, int index) {
        System.out.println("Adding " + action.name() + " to the front of the action list.");
        actions.add(index, action);
    }
    
    public void removeActionFromFront() {
        System.out.println("Removing " + actions.get(0) + " from action queue");
        actions.remove(0);
    }
    
    public void run() throws AWTException, IOException, InterruptedException {
        int hWnd = User32.instance.FindWindowA(null, "PPSSPP v1.13.1 - UCUS98751 : Patapon™3");
        Robot robot = new Robot();
        ArrayList<Action> newList = new ArrayList<Action>(actions);
        for (int iteration = 0; iteration < iterations; iteration++) {
            while (!actions.isEmpty()) {
                System.out.println(getCurrentAction().name());
                ScreenIdentifier.getScreenHandler(robot, hWnd).execute(robot, hWnd, this);
                Thread.sleep(300);
            }
            System.out.println("Script iteration " + iteration + " complete!");
            this.actions = newList;
        }
    }

    public ScriptBase(ArrayList<Action> actions, int iterations) {
        super();
        this.actions = actions;
        this.iterations = iterations;
    }
}
