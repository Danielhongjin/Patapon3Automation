package models;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;

import application.PataponAuto;
import backend.Logger;
import backend.ScreenIdentifier;
import backend.WindowGrab.User32;

/**
 * Contains logic for running multiple actions repeatedly.
 * @author Daniel Jin
 * @version 1.0
 */
public class ScriptBase {
    private ArrayList<Action> actions = new ArrayList<Action>();
    private int iterations;
    
    public Action getCurrentAction() {
        return actions.get(0);
    }
    
    public void addActionToFront(Action action ) {
        Logger.log("Adding " + action.name() + " to the front of the action list.", 1);
        actions.add(0, action);
    }
    
    public void addActionIndex(Action action, int index) {
        Logger.log("Adding " + action.name() + " to the front of the action list.", 1);
        actions.add(index, action);
    }
    
    public void removeActionFromFront() {
        Logger.log("Removing " + actions.get(0) + " from action queue", 1);
        actions.remove(0);
        if (actions.size() > 0) {
            Logger.log("Active action is now " + actions.get(0), 1);
        }
    }
    
    /**
     * Handles automated running of actions.
     */
    public void run() throws AWTException, IOException, InterruptedException {
        int hWnd = User32.instance.FindWindowA(null, PataponAuto.applicationName);
        String currentScreen = "";
        Robot robot = new Robot();
        ArrayList<Action> backupActionList = new ArrayList<Action>(actions);
        for (int iteration = 0; iteration < iterations; iteration++) {
            Logger.log("Script iteration " + iteration + " beginning.", 3);
            while (!actions.isEmpty()) {
                ScreenHandler screen = ScreenIdentifier.getScreenHandler(robot, hWnd);
                if (!currentScreen.equals(screen.getClass().getSimpleName())) {
                    currentScreen = screen.getClass().getSimpleName();
                    Logger.log("Found " + currentScreen + "!", 0);
                }
                screen.execute(robot, hWnd, this);
                Thread.sleep(300);
            }
            Logger.log("Script iteration " + iteration + " complete.", 3);
            this.actions = new ArrayList<Action>(backupActionList);
        }
    }

    /**
     * @param actions List of actions to run through.
     * @param iterations How many times the actions should be run through.
     */
    public ScriptBase(ArrayList<Action> actions, int iterations) {
        super();
        this.actions = actions;
        this.iterations = iterations;
    }
}
