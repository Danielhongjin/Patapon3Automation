package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import backend.InputController;
import types.Input;
import types.ScreenData;
import types.ScreenHandler;
import types.ScriptBase;

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
                Thread.sleep(250);
                InputController.processInput(Input.UP, robot);
                Thread.sleep(250);
                InputController.processInput(Input.CROSS, robot);
                Thread.sleep(250);
            }
        }
    }

}
