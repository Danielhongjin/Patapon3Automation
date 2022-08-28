package backend;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.PataponAuto;
import backend.WindowGrab.WindowInfo;

/**
 * Handles logging and screenshotting for the application.
 * @author Daniel Jin
 * @version 1.0
 */
public class Logger {
    protected static boolean[] logOptions = PataponAuto.logOptions;
    private static int screenshotCount = 0;

    /**
     * Takes a screenshot of the emulator.
     * @param windowID
     * @param robot
     * @throws IOException
     */
    public static void getScreenCapture(int windowID, Robot robot) throws IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        BufferedImage image = robot.createScreenCapture(
                new Rectangle(window.rect.left, window.rect.top, window.rect.right - window.rect.left, window.rect.bottom - window.rect.top));
        File outputfile = new File("screenshots/screenshot" + screenshotCount++ + ".png");
        ImageIO.write(image, "png", outputfile);
    }

    /**
     * Takes a screenshot of the emulator, using a Rectangle for the 
     * coordinates and size relative to the application position.
     * @param windowID
     * @param robot
     * @param rect
     * @throws IOException
     * @throws InterruptedException
     */
    public static void getScreenCaptureWithRect(int windowID, Robot robot, Rectangle rect) throws IOException, InterruptedException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        BufferedImage image = robot.createScreenCapture(new Rectangle(window.rect.left + rect.x, window.rect.top + rect.y, rect.width, rect.height));
        File outputfile = new File("screenshots/" + screenshotCount++ + ".png");
        ImageIO.write(image, "png", outputfile);
    }
    
    /**
     * Log level definition:
     * [0] = Screen change logging.
     * [1] = Action change logging.
     * [2] = Screen logic logging.
     * [3] = Execution change logging.
     * @param message Text to print
     * @param logType Log level
     */
    public static void log(String message, int logType) {
        if (logOptions[logType]) {
            String text = "";
            switch (logType) {
                case 0: {
                    text += "[Screen]  ";
                    break;
                }
                case 1: {
                    text += "[Action]  ";
                    break;
                }
                case 2: {
                    text += "[Input]   ";
                    break;
                }
                case 3: {
                    text += "[Execute] ";
                    break;
                }
            }
            text += java.time.LocalTime.now().toString().substring(0, 11);
            System.out.println(text + "  " + message);
        }
        
    }
    
}
