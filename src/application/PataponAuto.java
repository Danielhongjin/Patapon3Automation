package application;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.InputController;
import backend.ScreenIdentifier;
import backend.WindowGrab;
import backend.WindowGrab.User32;
import backend.WindowGrab.WindowInfo;
import types.Action;
import types.Input;
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

    public static void getScreenCaptureWithRect(int windowID, Robot robot, Rectangle rect) throws IOException, InterruptedException {
        String[] missions = { "PataponTrainingGrounds", "DownloadQuest", "FieldofAngryGiants", "ArenaofValor", "CaveofValor", "SnowFieldofSulliedTears",
                "RacingAlleyofPurity", "TowerofPurity", "PlateauofPompousWings", "RangeofJustice", "CastleofJustice", "GreedyMaskJungle", "ArenaofEarnestness",
                "EstateofEarnestness", "BottomlessStomachDesert", "RacingAlleyofRestraint", "LabyrinthofRestraint", "VolcanoZoneoftheLazyDemon",
                "RangeofAdamance", "EvilmassofAdamance", "SavannahofEnviousEyes", "ArenaofTolerance", "TombofTolerance" };
        for (int index = 0; index < missions.length; index++) {
            WindowInfo window = WindowGrab.getWindowInfo(windowID);
            BufferedImage image = robot.createScreenCapture(new Rectangle(window.rect.left + rect.x, window.rect.top + rect.y, rect.width, rect.height));
            File outputfile = new File("screenshots/" + missions[index] + ".png");
            ImageIO.write(image, "png", outputfile);
            InputController.processInput(Input.RIGHT, robot);
            Thread.sleep(1200);

        }

    }

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {

        int hWnd = User32.instance.FindWindowA(null, "PPSSPP v1.13.1 - UCUS98751 : Patapon™3");
        Robot robot = new Robot();
        Thread.sleep(1200);
        getScreenCapture(hWnd, robot);

//        getScreenCaptureWithRect(hWnd, robot, new Rectangle(504, 93, 393, 19));
        ScreenHandler screenHandler = ScreenIdentifier.getScreenHandler(robot, hWnd);

        ScreenHandler.addActionToFront(Action.TOBONEDETHBRIGATE);
        ScreenHandler.addActionToFront(Action.TOMISSIONSELECT);
        screenHandler.execute(robot, hWnd);
//        Thread.sleep(1000);
//        Sequence[] sequence = { new AttackSequence(), new MoveSequence() };
//        
//        ScreenHandler.addActionToFront(Action.TOBONEDETHBRIGATE);
//        GameplayScreenHandler chargeFarm = new GameplayScreenHandler(sequence, robot, hWnd, 10000);
//        chargeFarm.execute();
    }
}
