package types;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import backend.ScreenIdentifier;
import backend.WindowGrab.WindowInfo;


public abstract class ScreenHandler {
    public static ArrayList<Action> actions = new ArrayList<Action>();
    protected Robot robot;
    protected int windowID;
    protected ScreenData screenData;
    /*
     * runspeed should match PPSSPP's alternative speed setting. 300% => 3.
     * max value for runspeed differs per machine, only set to a speed if ppsspp
     * can reliably keep at least 93% of that speed in fps.
     */
    public static double runSpeed = 5;
    /*
     * Disable to hide printlns.
     */
    public static boolean logging = true;
    
    public ScreenData getScreenData() {
        return screenData;
    }

    public void execute(Robot robot, int windowID) throws InterruptedException, IOException {
        System.out.println("Undefined script action execute! Please define execute() for ScreenHandlers.");
    };
    
    public static Action getCurrentAction() {
        return actions.get(0);
    }
    
    public static void addActionToFront(Action action ) {
        System.out.println("Adding " + action.name() + " to the front of the action list.");
        actions.add(0, action);
    }
    
    public static void removeActionFromFront() {
        System.out.println("Removing " + actions.get(0) + " from action queue");
        actions.remove(0);
    }
    
    public boolean isOnScreen(WindowInfo window) {
        BufferedImage image = ScreenIdentifier.getImage(screenData.getImagePath());
        BufferedImage screenCurrent = ScreenIdentifier.getCapture(robot, window,
                getScreenData().getRect(window));
        if (ScreenIdentifier.bufferedImagesEqual(image, screenCurrent)) {
            return true;
        } else {
            return false;
        }
    }

    public ScreenHandler(ScreenData screenData) {
        super();
        this.screenData = screenData;
    }
    
    
    
}
