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

public class PostGameStage2ScreenHandler extends ScreenHandler {
    int phase = 0;

    public PostGameStage2ScreenHandler() {
        super();
    }

    public void execute(Robot robot, int windowID) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        while (true) {
            switch (getCurrentAction()) {
                case TOHOME: {
                    WindowInfo window = WindowGrab.getWindowInfo(windowID);
                    ControlController.processInput(Input.CROSS, robot);
                    BufferedImage image = ScreenIdentifier.getImage("PostGameStage1");
                    BufferedImage screenCurrent = ScreenIdentifier.getCapture(robot, window,
                            ScreenDataDB.getScreen("PostGameStage2").getRect(window));
                    if (!ScreenIdentifier.bufferedImagesEqual(image, screenCurrent)) {
                        return;
                    } 
                }
                default: {
                    ScreenHandler.addActionToFront(Action.TOHOME);
                }
            }
        }
    };

}
