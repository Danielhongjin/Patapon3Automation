package backend;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.WindowGrab.WindowInfo;
import data.screendata.ScreenDataDB;
import models.ScreenData;
import models.ScreenHandler;
import screenhandlers.NullScreenHandler;

/**
 * Contains methods for identifying screens via images comparisons.
 * @author Daniel Jin
 * @version 1.0
 */
public class ScreenIdentifier {
    public static BufferedImage getCapture(Robot robot, WindowInfo window, Rectangle rect) {
        return robot.createScreenCapture(rect);
    }
    
    /**
     * Gets a BufferedImage from a file in screens folder.
     * @param imagePath
     * @return
     */
    public static BufferedImage getImage(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("screens/" + imagePath + ".png"));
        } catch (IOException e) {
        }
        return img;
    }
    
    /**
     * Checks for buffered image rough equality, only needs to satisfy 80% of pixel matches..
     * @param imgage1
     * @param image2
     * @return
     */
    public static boolean bufferedImagesEqual(BufferedImage imgage1, BufferedImage image2) {
        if (imgage1.getWidth() == image2.getWidth() && imgage1.getHeight() == image2.getHeight()) {
            int correctCutoff = (int) (imgage1.getWidth() * imgage1.getHeight() * 0.8);
            int currentCorrect = 0;
            for (int x = 0; x < imgage1.getWidth(); x++) {
                for (int y = 0; y < imgage1.getHeight(); y++) {
                    if (imgage1.getRGB(x, y) == image2.getRGB(x, y)) {
                        currentCorrect++;
                        if (currentCorrect > correctCutoff) return true;
                    }
                        
                }
            }
        }
        return false;
    }
    
    /**
     * Goes through all ScreenHandlers in ScreenDataDB and checks for image equality. If it finds a match,
     * returns the ScreenHandler. If none are found, returns a NullScreenHandler.
     * @param robot
     * @param windowID
     * @return
     * @throws IOException
     */
    public static ScreenHandler getScreenHandler(Robot robot, int windowID) throws IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        ScreenHandler[] screens = ScreenDataDB.getScreens();
        for (int index = 0; index < screens.length; index++) {
            BufferedImage screenImage = getImage(screens[index].getScreenData().getImagePath());
            BufferedImage screenCurrent = getCapture(robot, window, screens[index].getScreenData().getRect(window));
            if (bufferedImagesEqual(screenImage, screenCurrent)) {
                return screens[index];
            }
        }
        return new NullScreenHandler(new ScreenData(0, 0, 0, 0, "null"));
    }
}
