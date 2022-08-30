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
 * ScreenHandler for the mission results screen.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class PostGameStage2ScreenHandler extends ScreenHandler {
    int phase = 0;

    public PostGameStage2ScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        
        TimeController.sleep(1000);
        switch (script.getCurrentAction()) {
            default: {
                InputController.processInput(Input.CROSS, robot);
                TimeController.sleep(200);
                break;
            }
        }

    };

}
