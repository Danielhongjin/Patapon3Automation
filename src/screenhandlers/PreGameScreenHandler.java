package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import backend.InputController;
import models.Input;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;

/**
 * ScreenHandler for the pregame loadout screen.
 * @author Daniel Jin
 * @version 1.0
 */
public class PreGameScreenHandler extends ScreenHandler {

    public PreGameScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        Thread.sleep(400);
        switch (script.getCurrentAction()) {
            case TOHOME: {
                InputController.processInput(Input.CIRCLE, robot);
                break;
            }
            default: {
                Thread.sleep(350);
                InputController.processInput(Input.UP, robot);
                Thread.sleep(350);
                InputController.processInput(Input.CROSS, robot);
                Thread.sleep(400);
                break;
            }
        }
    }

}
