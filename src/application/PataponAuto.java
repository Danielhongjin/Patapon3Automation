package application;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import backend.InputController;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import types.Action;
import types.Input;
import types.ScriptBase;
import types.Sequence;
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
    public static double runSpeed = 3;
    /*
     * [0] = Screen change logging
     * [1] = Action change logging
     * [2] = Screen logic logging
     */
    public static boolean[] logOptions = {true, true, true};

    public static void getScreenCapture(int windowID, Robot robot) throws IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        BufferedImage image = robot.createScreenCapture(
                new Rectangle(window.rect.left, window.rect.top, window.rect.right - window.rect.left, window.rect.bottom - window.rect.top));
        File outputfile = new File("screenshots/screenshot" + screenshotCount++ + ".png");
        ImageIO.write(image, "png", outputfile);
    }

    public static void getScreenCaptureWithRect(int windowID, Robot robot, Rectangle rect) throws IOException, InterruptedException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        BufferedImage image = robot.createScreenCapture(new Rectangle(window.rect.left + rect.x, window.rect.top + rect.y, rect.width, rect.height));
        File outputfile = new File("screenshots/" + screenshotCount++ + ".png");
        ImageIO.write(image, "png", outputfile);
    }

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        Thread.sleep(1200);
        ArrayList<Action> actions = new ArrayList<Action>(
                Arrays.asList(Action.TOHOME, Action.TOMISSIONSELECT, Action.TOBONEDETHONTHECLIFF, Action.TOGAMEPLAY, Action.TOHOME));
        Sequence[] sequences = new Sequence[] { new MoveSequence(), new AttackSequence() };
        GameplayScreenHandler.iterations = 9990;
        GameplayScreenHandler.setSequences(sequences);
        ScriptBase script = new ScriptBase(actions, 10);
        script.run();
    }
}
