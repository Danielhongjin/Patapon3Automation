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
 * ScreenHandler for the deploy w/o all set skills set menu, should probably be merged into PreGameScreenHandler.
 * @author Daniel Jin
 * @version 1.0
 */
public class SetSkillDeployScreenHandler extends ScreenHandler {

    public SetSkillDeployScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (script.getCurrentAction()) {
            case TOHOME: {
                InputController.processInput(Input.CIRCLE, robot);
            }
            default: {
                TimeController.sleep(250);
                InputController.processInput(Input.UP, robot);
                TimeController.sleep(250);
                InputController.processInput(Input.CROSS, robot);
                TimeController.sleep(250);
            }
        }
    }

}
