package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import backend.InputController;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;
import types.Input;

/**
 * ScreenHandler for the deploy with all set skills set menu, should probably be merged into PreGameScreenHandler.
 * @author Daniel Jin
 * @version 1.0
 */
public class DeployScreenHandler extends ScreenHandler {

    public DeployScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (script.getCurrentAction()) {
            case TOHOME: {
                InputController.processInput(Input.CIRCLE, robot);
                break;
            }
            default: {
                InputController.processInput(Input.UP, robot);
                InputController.processInput(Input.CROSS, robot);
                break;
            }
        }
    }

}
