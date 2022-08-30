package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import backend.InputController;
import backend.TimeController;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;
import types.Input;

/**
 * ScreenHandler for the treasure opening screen.
 * @author Daniel Jin
 * @version 1.0
 */
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
            default: {
                InputController.processInput(Input.CROSS, robot);
                TimeController.sleep(1000);
                InputController.processInput(Input.LEFT, robot);
                TimeController.sleep(1000);
            }
        }
    }

}
