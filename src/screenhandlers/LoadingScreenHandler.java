package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import backend.InputController;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import types.Input;
import types.ScreenData;
import types.ScreenHandler;
import types.ScriptBase;

public class LoadingScreenHandler extends ScreenHandler {
    int phase = 0;

    public LoadingScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (script.getCurrentAction()) {
            default: {
                while (true) {
                    WindowInfo window = WindowGrab.getWindowInfo(windowID);
                    if (!isOnScreen(window)) {
                        InputController.processInput(Input.CROSS, robot);
                        break;
                    } else {
                        Thread.sleep(500);
                    }
                }
            }
        }
    }

}
