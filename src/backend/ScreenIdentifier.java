package backend;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.WindowGrab.WindowInfo;
import data.screendata.ScreenDataDB;
import types.ScreenData;
import types.ScreenHandler;

public class ScreenIdentifier {
    public static BufferedImage getCapture(Robot robot, WindowInfo window, Rectangle rect) {
        return robot.createScreenCapture(rect);
    }
    
    public static BufferedImage getImage(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("screens/" + imagePath + ".png"));
        } catch (IOException e) {
        }
        return img;
    }
    
    public static boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
    
    public static ScreenHandler getScreenHandler(Robot robot, int windowID) throws IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        ScreenHandler[] screens = ScreenDataDB.getScreens();
        for (int index = 0; index < screens.length; index++) {
            BufferedImage screenImage = getImage(screens[index].getScreenData().getImagePath());
            BufferedImage screenCurrent = getCapture(robot, window, screens[index].getScreenData().getRect(window));
            if (bufferedImagesEqual(screenImage, screenCurrent)) {
                System.out.println("Found " + screens[index].getClass().getSimpleName() + "!");
                return screens[index];
            }
        }
        return null;
    }
}
