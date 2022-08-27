package application;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.ScreenIdentifier;
import backend.WindowGrab;
import backend.WindowGrab.User32;
import backend.WindowGrab.WindowInfo;
import types.Action;
import types.ScreenHandler;
import types.Sequence;
import data.*;
import data.sequences.AttackSequence;
import data.sequences.MoveSequence;
import screenhandlers.GameplayScreenHandler;

/*
 * Application to automate various aspects of the PSP game Patapon 3. Currently depends on a specific window size setting of 2x and manual code modification.
 * @author Daniel Jin
 * @version 1.0
 */
public class PataponAuto {
    public static int screenshotCount = 0;
    
    public static void getScreenCapture(int windowID, Robot robot) throws IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        BufferedImage image = robot.createScreenCapture(
                new Rectangle(window.rect.left, window.rect.top, window.rect.right - window.rect.left, window.rect.bottom - window.rect.top));
        File outputfile = new File("screenshots/screenshot" + screenshotCount++ + ".png");
        ImageIO.write(image, "png", outputfile);
    }

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        int hWnd = User32.instance.FindWindowA(null, "PPSSPP v1.13.1 - UCUS98751 : Patapon™3");
        Robot robot = new Robot();
        getScreenCapture(hWnd, robot);
        ScreenHandler screenHandler = ScreenIdentifier.getScreenHandler(robot, hWnd);
        ScreenHandler.addActionToFront(Action.TOHOME);
        screenHandler.execute(robot, hWnd);
//        Thread.sleep(1000);
//        Sequence[] sequence = { new AttackSequence(), new MoveSequence() };
//        
//        ScreenHandler.addActionToFront(Action.TOBONEDETHBRIGATE);
//        GameplayScreenHandler chargeFarm = new GameplayScreenHandler(sequence, robot, hWnd, 10000);
//        chargeFarm.execute();
    }
}
