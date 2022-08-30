package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;

/**
 * ScreenHandler for when a ScreenHandler cannot be found.
 * @author Daniel Jin
 * @version 1.0
 */
public class NullScreenHandler extends ScreenHandler {

    public NullScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        switch (script.getCurrentAction()) {
            default: {
            }
        }
    }

}
