package screenhandlers;

import java.awt.Robot;
import java.io.IOException;

import types.ScreenData;
import types.ScreenHandler;
import types.ScriptBase;

public class NullScreenHandler extends ScreenHandler {

    public NullScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        switch (script.getCurrentAction()) {
            default: {
                System.out.println("Screen cannot be found.");
                Thread.sleep(1000);
            }
        }
    }

}
