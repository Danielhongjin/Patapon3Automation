package backend;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.WindowGrab.WindowInfo;
import data.ScreenDataDB;
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
        return robot.createScreenCapture(new Rectangle(window.rect.left + rect.x, window.rect.top + rect.y,  rect.width, rect.height));
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
     * Checks for buffered image rough equality, only needs to satisfy 85% of pixel matches.
     * @param image1
     * @param image2
     * @return
     */
    public static boolean bufferedImagesEqual(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() == image2.getWidth() && image1.getHeight() == image2.getHeight()) {
            int correctCutoff = (int) (image1.getWidth() * image1.getHeight() * 0.85);
            int currentCorrect = 0;
            for (int x = 0; x < image1.getWidth(); x++) {
                for (int y = 0; y < image1.getHeight(); y++) {
                    Color pixel1 = new Color(image1.getRGB(x, y));
                    Color pixel2 = new Color(image2.getRGB(x, y));
                    if (Math.abs(pixel1.getRed() - pixel2.getRed()) < 10  && Math.abs(pixel1.getGreen() - pixel2.getGreen()) < 10 && Math.abs(pixel1.getBlue() - pixel2.getBlue()) < 10) {
                        currentCorrect++;
                        if (currentCorrect > correctCutoff) return true;
                        
                    }
                        
                }
            }
        }
        return false;
    }
    
    /**
     * Gets the percentage of roughly equal pixels between two images.
     * @param image1
     * @param image2
     * @return percentage of roughly identical pixels.
     */
    public static double getImageRelatedness(BufferedImage image1, BufferedImage image2) {
        int currentCorrect = 0;
        for (int x = 0; x < image1.getWidth(); x++) {
            for (int y = 0; y < image1.getHeight(); y++) {
                Color pixel1 = new Color(image1.getRGB(x, y));
                Color pixel2 = new Color(image2.getRGB(x, y));
                if (Math.abs(pixel1.getRed() - pixel2.getRed()) < 15 && Math.abs(pixel1.getGreen() - pixel2.getGreen()) < 15
                        && Math.abs(pixel1.getBlue() - pixel2.getBlue()) < 15) {
                    currentCorrect++;
                }

            }
        }
        return (1.0 * currentCorrect / (image1.getWidth() * image1.getHeight()));
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
