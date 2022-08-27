package screenhandlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import backend.InputController;
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

    public PostGameStage1ScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    public void execute(Robot robot, int windowID) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (getCurrentAction()) {
            case TOHOME: {
                while (true) {
                    WindowInfo window = WindowGrab.getWindowInfo(windowID);
                    InputController.processInput(Input.CROSS, robot);
                    InputController.processInput(Input.LEFT, robot);
                    if (!isOnScreen(window)) {
                        return;
                    }
                }
            }
            default: {
                ScreenHandler.addActionToFront(Action.TOHOME);
            }
        }
    }

}
