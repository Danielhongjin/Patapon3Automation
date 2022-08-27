package types;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;

import application.PataponAuto;
import backend.ScreenIdentifier;
import backend.WindowGrab.User32;

public class ScriptBase {
    private ArrayList<Action> actions = new ArrayList<Action>();
    private int iterations;
    
    public Action getCurrentAction() {
        return actions.get(0);
    }
    
    public void addActionToFront(Action action ) {
        if (PataponAuto.logOptions[1]) System.out.println("[ActionChange]\tAdding " + action.name() + " to the front of the action list.");
        actions.add(0, action);
    }
    
    public void addActionIndex(Action action, int index) {
        if (PataponAuto.logOptions[1]) System.out.println("[ActionChange]\tAdding " + action.name() + " to the front of the action list.");
        actions.add(index, action);
    }
    
    public void removeActionFromFront() {
        if (PataponAuto.logOptions[1]) System.out.println("[ActionChange]\tRemoving " + actions.get(0) + " from action queue");
        actions.remove(0);
        if (actions.size() > 0) {
            if (PataponAuto.logOptions[1]) System.out.println("[ActionChange]\tActive action is now " + actions.get(0));
        }
    }
    
    public void run() throws AWTException, IOException, InterruptedException {
        int hWnd = User32.instance.FindWindowA(null, "PPSSPP v1.13.1 - UCUS98751 : Patapon™3");
        String currentScreen = "";
        Robot robot = new Robot();
        ArrayList<Action> backupActionList = new ArrayList<Action>(actions);
        for (int iteration = 0; iteration < iterations; iteration++) {
            System.out.println("[Script]\tScript iteration " + iteration + " beginning.");
            while (!actions.isEmpty()) {
                ScreenHandler screen = ScreenIdentifier.getScreenHandler(robot, hWnd);
                if (PataponAuto.logOptions[0] && !currentScreen.equals(screen.getClass().getSimpleName())) {
                    currentScreen = screen.getClass().getSimpleName();
                    System.out.println("[ScreenChange]\tFound " + currentScreen + "!");
                }
                screen.execute(robot, hWnd, this);
                Thread.sleep(300);
            }
            System.out.println("[Script]\tScript iteration " + iteration + " complete.");
            this.actions = new ArrayList<Action>(backupActionList);
        }
    }

    public ScriptBase(ArrayList<Action> actions, int iterations) {
        super();
        this.actions = actions;
        this.iterations = iterations;
    }
}
