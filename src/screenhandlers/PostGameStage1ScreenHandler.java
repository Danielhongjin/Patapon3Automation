package screenhandlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import backend.ControlController;
import backend.ScreenIdentifier;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import data.screendata.ScreenDataDB;
import types.Action;
import types.Input;
import types.ScreenData;
import types.ScreenHandler;

public class PostGameStage1ScreenHandler extends ScreenHandler {
    int phase = 0;

    public PostGameStage1ScreenHandler() {
        super();
    }

    public void execute(Robot robot, int windowID) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        while (true) {
            switch (getCurrentAction()) {
                case TOHOME: {
                    ControlController.processInput(Input.CROSS, robot);
                    ControlController.processInput(Input.LEFT, robot);
                    BufferedImage image = ScreenIdentifier.getImage("PostGameStage1");
                    BufferedImage screenCurrent = ScreenIdentifier.getCapture(robot, window,
                            ScreenDataDB.getScreen("PostGameStage1").getRect(window));
                    if (!ScreenIdentifier.bufferedImagesEqual(image, screenCurrent)) {
                        return;
                    } 
                }
                default: {
                    ScreenHandler.addActionToFront(Action.TOHOME);
                }
            }
            Thread.sleep(500);
        }
        
    };

}
