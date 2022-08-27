package types;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import backend.ScreenIdentifier;
import backend.WindowGrab.WindowInfo;


public abstract class ScreenHandler {
    
    protected Robot robot;
    protected int windowID;
    protected ScreenData screenData;
    /*
     * Disable to hide printlns.
     */
    public static boolean logging = true;
    
    public ScreenData getScreenData() {
        return screenData;
    }

    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        System.out.println("Undefined script action execute! Please define execute() for ScreenHandlers.");
    };
    
    
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
