package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import application.PataponAuto;
import backend.InputController;
import models.Input;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;

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
        
        Thread.sleep(1000);
        switch (script.getCurrentAction()) {
            default: {
                InputController.processInput(Input.CROSS, robot);
                Thread.sleep((long) (200 / PataponAuto.runSpeed));
                break;
            }
        }

    };

}
