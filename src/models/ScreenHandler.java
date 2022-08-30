package models;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import application.PataponAuto;
import backend.Logger;
import backend.ScreenIdentifier;
import backend.WindowGrab.WindowInfo;
import types.LogType;

/**
 * Contains data and logic used to execute continue execution for each screen.
 * @author Daniel Jin
 * @version 1.0
 */
public abstract class ScreenHandler {
    
    protected Robot robot;
    protected int windowID;
    protected ScreenData screenData;
    protected static boolean[] logOptions = PataponAuto.logOptions;
    
    public ScreenData getScreenData() {
        return screenData;
    }

    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        Logger.log("Undefined script action execute! Please define execute() for ScreenHandlers.", LogType.ERROR);
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
