package application;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import backend.WindowGrab.User32;
import types.Action;
import types.ScreenHandler;
import types.Sequence;
import data.*;
import screenhandlers.GameplayScreenHandler;

/*
 * Application to automate various aspects of the PSP game Patapon 3. Currently depends on a specific window size setting of 2x and manual code modification.
 * @author Daniel Jin
 * @version 1.0
 */
public class PataponAuto {

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        int hWnd = User32.instance.FindWindowA(null, "PPSSPP v1.13.1 - UCUS98751 : Patapon™3");
        Robot robot = new Robot();
        Thread.sleep(1000);
        Sequence[] sequence = { new AttackSequence(), new MoveSequence() };
        
        ScreenHandler.addActionToFront(Action.TOBONEDETHBRIGATE);
        GameplayScreenHandler chargeFarm = new GameplayScreenHandler(sequence, robot, hWnd, 10000);
        chargeFarm.execute();
    }
}
