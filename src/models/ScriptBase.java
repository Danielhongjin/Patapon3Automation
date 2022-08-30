package models;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;

import application.PataponAuto;
import backend.Logger;
import backend.ScreenIdentifier;
import backend.WindowGrab.User32;
import types.Action;
import types.LogType;

/**
 * Contains logic for running multiple actions repeatedly.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class ScriptBase {
    public enum Stage {
        STARTUP, MAIN, TEARDOWN
    }
    
    private int ID;
    private String name;
    /**
     * Actions that will be run at the start of the script's execution.
     * Is not affected by iterations, is affected by numberOfRuns.
     */
    private ArrayList<Action> startup = new ArrayList<Action>();
    /**
     * Actions that will be run after startup.
     * Is affected by both iterations and numberOfRuns.
     */
    private ArrayList<Action> mainActions = new ArrayList<Action>();
    /**
     * Actions that will be run at the end of the script's execution.
     * Is not affected by iterations, is affected by numberOfRuns.
     */
    private ArrayList<Action> teardown = new ArrayList<Action>();
    /**
     * Number of times the main actions will be run.
     */
    private int iterations;
    /**
     * Number of times this script will run.
     */
    private int numberOfRuns = 1;
    private Stage stage = Stage.STARTUP;
    private String currentScreen = "";

    public void setStartup(ArrayList<Action> startup) {
        this.startup = startup;
    }

    public void setTeardown(ArrayList<Action> teardown) {
        this.teardown = teardown;
    }

    /**
     * Returns whether the current stage is empty.
     * @return
     */
    public boolean isEmpty() {
        switch (stage) {
            case STARTUP: {
                return startup.isEmpty();
            }
            case MAIN: {
                return mainActions.isEmpty();
            }
            case TEARDOWN: {
                return teardown.isEmpty();
            }
            default: {
                Logger.log("Unrecognized ScriptBase stage recognized, how on earth could this have happened?", LogType.ERROR);
                return true;
            }
        }
    }

    /**
     * Returns the current stage's current action.
     * @return
     */
    public Action getCurrentAction() {
        switch (stage) {
            case STARTUP: {
                return startup.get(0);
            }
            case MAIN: {
                return mainActions.get(0);
            }
            case TEARDOWN: {
                return teardown.get(0);
            }
            default: {
                Logger.log("Unrecognized ScriptBase stage recognized, how on earth could this have happened?", LogType.ERROR);
                return null;
            }
        }
        
    }

    /**
     * Adds an action to the current stage.
     * @param action
     */
    public void addActionToFront(Action action) {
        switch (stage) {
            case STARTUP: {
                startup.add(0, action);
                Logger.log("Adding " + action.name() + " to the front of the startup action list.", LogType.ACTIONCHANGE);
                break;
            }
            case MAIN: {
                mainActions.add(0, action);
                Logger.log("Adding " + action.name() + " to the front of the main action list.", LogType.ACTIONCHANGE);
                break;
            }
            case TEARDOWN: {
                teardown.add(0, action);
                Logger.log("Adding " + action.name() + " to the front of the teardown action list.", LogType.ACTIONCHANGE);
                break;
            }
            default: {
                Logger.log("Unrecognized ScriptBase stage recognized, how on earth could this have happened?", LogType.ERROR);
            }
        }

    }

    /**
     * Adds an action to the index of the current stage's action list.
     * @param action
     * @param index
     */
    public void addActionIndex(Action action, int index) {
        switch (stage) {
            case STARTUP: {
                startup.add(index, action);
                Logger.log("Adding " + action.name() + " to the index " + index + " of the startup action list.", LogType.ACTIONCHANGE);
                break;
            }
            case MAIN: {
                mainActions.add(index, action);
                Logger.log("Adding " + action.name() + " to the index " + index + " of the main action list.", LogType.ACTIONCHANGE);
                break;
            }
            case TEARDOWN: {
                teardown.add(index, action);
                Logger.log("Adding " + action.name() + " to the index " + index + " of the teardown action list.", LogType.ACTIONCHANGE);
                break;
            }
            default: {
                Logger.log("Unrecognized ScriptBase stage recognized, how on earth could this have happened?", LogType.ERROR);
            }
        }
    }

    /**
     * Removes an action from the current stage.
     * @return the removed action.
     */
    public Action removeActionFromFront() {
        switch (stage) {
            case STARTUP: {
                Logger.log("Removing " + startup.get(0) + " from the startup action queue.", LogType.ACTIONCHANGE);
                if (startup.size() != 1) {
                    Logger.log("Active action is now " + startup.get(1), LogType.ACTIONCHANGE);
                }
                return startup.remove(0);
            }
            case MAIN: {
                Logger.log("Removing " + mainActions.get(0) + " from the main action queue.", LogType.ACTIONCHANGE);
                if (mainActions.size() != 1) {
                    Logger.log("Active action is now " + mainActions.get(1), LogType.ACTIONCHANGE);
                }
                return mainActions.remove(0);
            }
            case TEARDOWN: {
                Logger.log("Removing " + teardown.get(0) + " from the teardown action queue.", LogType.ACTIONCHANGE);
                if (teardown.size() != 1) {
                    Logger.log("Active action is now " + teardown.get(1), LogType.ACTIONCHANGE);
                }
                return teardown.remove(0);
            }
            default: {
                Logger.log("Unrecognized ScriptBase stage recognized, how on earth could this have happened?", LogType.ERROR);
            }
        }
        return null;
    }
    
    public void reportScreenName(ScreenHandler screen)  {
        if (!currentScreen.equals(screen.getClass().getSimpleName())) {
            currentScreen = screen.getClass().getSimpleName();
            Logger.log("Found " + currentScreen + "!", LogType.SCREENCHANGE);
        }
    }

    /**
     * Handles automated running of actions.
     */
    public void run() throws AWTException, IOException, InterruptedException {
        int hWnd = User32.instance.FindWindowA(null, PataponAuto.applicationName);
        
        Robot robot = new Robot();
        
        ArrayList<Action> backupStartupList = new ArrayList<Action>();
        if (startup != null) {
            backupStartupList = new ArrayList<Action>(startup);
        } else this.startup = new ArrayList<Action>();
        ArrayList<Action> backupActionList = new ArrayList<Action>(mainActions);
        ArrayList<Action> backupTeardownList = new ArrayList<Action>();
        if (teardown != null) {new ArrayList<Action>();
            backupTeardownList = new ArrayList<Action>(teardown);
        } else this.teardown = new ArrayList<Action>();

        
        Logger.log("Script " + name + "#" + ID + " is starting.", LogType.EXECUTION);
        
        for (int run = 0; run < numberOfRuns; run++) {
            Logger.log("Run " + run + " starting.", LogType.EXECUTION);
            stage = Stage.STARTUP;
            if (!startup.isEmpty()) {
                Logger.log("Executing startup actions.", LogType.EXECUTION);
                while (!startup.isEmpty()) {
                    ScreenHandler screen = ScreenIdentifier.getScreenHandler(robot, hWnd);
                    reportScreenName(screen);
                    screen.execute(robot, hWnd, this);
                    Thread.sleep((long) (300 / PataponAuto.runSpeed));
                }
            }
            Logger.log("Executing main actions.", LogType.EXECUTION);
            for (int iteration = 0; iteration < iterations; iteration++) {
                Logger.log("Script iteration " + iteration + " beginning.", LogType.EXECUTION);
                stage = Stage.MAIN;
                while (!mainActions.isEmpty()) {
                    ScreenHandler screen = ScreenIdentifier.getScreenHandler(robot, hWnd);
                    reportScreenName(screen);
                    screen.execute(robot, hWnd, this);
                    Thread.sleep((long) (300 / PataponAuto.runSpeed));
                }
                Logger.log("Script iteration " + iteration + " complete.", LogType.EXECUTION);
            }
            stage = Stage.TEARDOWN;
            if (!teardown.isEmpty()) {
                Logger.log("Executing teardown actions.", LogType.EXECUTION);
                while (!teardown.isEmpty()) {
                    ScreenHandler screen = ScreenIdentifier.getScreenHandler(robot, hWnd);
                    reportScreenName(screen);
                    screen.execute(robot, hWnd, this);
                    Thread.sleep((long) (300 / PataponAuto.runSpeed));
                }
            }
            this.startup = new ArrayList<Action>(backupStartupList);
            this.mainActions = new ArrayList<Action>(backupActionList);
            this.teardown = new ArrayList<Action>(backupTeardownList);
            Logger.log("Run " + run + " complete.", LogType.EXECUTION);
        }
        Logger.log("Script " + name + "#" + ID + " has completed.", LogType.EXECUTION);
    }

    /**
     * Type that runs actions.
     * @param name
     * @param startup
     * @param actions
     * @param teardown
     * @param numberOfRuns How many times the script will run.
     * @param iterations How many times the main actions will run.
     */
    public ScriptBase(int ID, String name, ArrayList<Action> startup, ArrayList<Action> actions, ArrayList<Action> teardown, int numberOfRuns, int iterations) {
        super();
        this.ID = ID;
        this.name = name;
        this.startup = startup;
        this.mainActions = actions;
        this.teardown = teardown;
        this.numberOfRuns = numberOfRuns;
        this.iterations = iterations;
    }
}
