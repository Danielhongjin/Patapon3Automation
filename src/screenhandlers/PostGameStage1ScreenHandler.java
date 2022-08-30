package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import application.PataponAuto;
import backend.InputController;
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
                Thread.sleep((long) (1000 / PataponAuto.runSpeed));
                InputController.processInput(Input.LEFT, robot);
                Thread.sleep((long) (1000 / PataponAuto.runSpeed));
            }
        }
    }

}
