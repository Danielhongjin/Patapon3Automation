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
                Thread.sleep((long) (250 / PataponAuto.runSpeed));
                InputController.processInput(Input.UP, robot);
                Thread.sleep((long) (250 / PataponAuto.runSpeed));
                InputController.processInput(Input.CROSS, robot);
                Thread.sleep((long) (250 / PataponAuto.runSpeed));
            }
        }
    }

}
