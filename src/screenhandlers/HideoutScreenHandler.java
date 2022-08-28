package screenhandlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import backend.InputController;
import backend.ScreenIdentifier;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import models.Input;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;

/**
 * ScreenHandler for the hideout.
 * @author Daniel Jin
 * @version 1.0
 */
public class HideoutScreenHandler extends ScreenHandler {
    int phase = 0;
    Rectangle buildingNameRect = new Rectangle(403, 520, 174, 26);
    String[] buildings = { "Blacksmith", "HeroGate", "Armory", "TeamTotem", "Barracks", "Sukopon", "MasterObelisk", "SilverHoshipon", "BattleGate",
            "Meden" };

    public HideoutScreenHandler(ScreenData screenData) {
        super(screenData);
    }
    
    /**
     * Finds image for specified building.
     * @param imagePath
     * @return
     */
    public static BufferedImage getBuildingImage(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("screens/Hideout/" + imagePath + ".png"));
        } catch (IOException e) {
        }
        return img;
    }
    
    /**
     * Checks for current selected building and decides which input to use accordingly.
     * @param target the building being aimed for.
     * @throws InterruptedException
     * @throws IOException
     */
    public void navigateToBuilding(String target) throws InterruptedException, IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        for (int index = 0; index < buildings.length; index++) {
            BufferedImage screenImage = getBuildingImage(buildings[index]);
            BufferedImage screenCurrent = ScreenIdentifier.getCapture(robot, window, new Rectangle(window.rect.left + buildingNameRect.x, window.rect.top + buildingNameRect.y, buildingNameRect.width, buildingNameRect.height));
            if (ScreenIdentifier.bufferedImagesEqual(screenImage, screenCurrent)) {
                int currentLocation = Arrays.asList(buildings).indexOf(buildings[index]);
                int destination = Arrays.asList(buildings).indexOf(target);
                if (destination > currentLocation) {
                    InputController.processInput(Input.RIGHT, robot);
                } else if (destination < currentLocation) {
                    InputController.processInput(Input.LEFT, robot);
                } else {
                    InputController.processInput(Input.CROSS, robot);
                }
            }
        }
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (script.getCurrentAction()) {
            case TOMISSIONSELECT: {
                navigateToBuilding("MasterObelisk");
                break;
            }
            case TOHOME: {
                script.removeActionFromFront();
                break;
            }
            default: {
                
            }
        }
    }

}
