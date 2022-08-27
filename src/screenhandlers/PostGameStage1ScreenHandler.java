package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import application.PataponAuto;
import backend.InputController;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import types.Input;
import types.ScreenData;
import types.ScreenHandler;
import types.ScriptBase;

public class PostGameStage1ScreenHandler extends ScreenHandler {
    int phase = 0;

    public PostGameStage1ScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (script.getCurrentAction()) {
            case TOHOME: {
                while (true) {
                    WindowInfo window = WindowGrab.getWindowInfo(windowID);
                    InputController.processInput(Input.CROSS, robot);
                    Thread.sleep((long) (1000 / PataponAuto.runSpeed));
                    InputController.processInput(Input.LEFT, robot);
                    Thread.sleep((long) (1000 / PataponAuto.runSpeed));
                    if (!isOnScreen(window)) {
                        return;
                    }
                }
            }
            default: {
                script.removeActionFromFront();
            }
        }
    }

}
