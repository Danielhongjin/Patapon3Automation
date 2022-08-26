package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import types.ScreenHandler;

public class AfterGameScreenHandler extends ScreenHandler {

    public AfterGameScreenHandler(Robot robot, int windowID) {
        super(robot, windowID);
    }
    
    public void execute() throws InterruptedException, IOException {
        switch (getCurrentAction()) {
            default: {
                
            }
        }
    };

}
