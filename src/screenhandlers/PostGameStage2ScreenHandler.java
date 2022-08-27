package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import backend.InputController;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import types.Action;
import types.Input;
import types.ScreenData;
import types.ScreenHandler;
import types.ScriptBase;

public class PostGameStage2ScreenHandler extends ScreenHandler {
    int phase = 0;

    public PostGameStage2ScreenHandler(ScreenData screenData) {
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
                    if (!isOnScreen(window)) {
                        return;
                    }
                }
            }
            case GAMEPLAY: {
                
                script.addActionToFront(Action.TOHOME);
            }
            default: {
                
            }
        }

    };

}
