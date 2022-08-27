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
import data.screendata.ScreenDataDB;
import types.Action;
import types.Input;
import types.ScreenData;
import types.ScreenHandler;

public class HideoutScreenHandler extends ScreenHandler {
    int phase = 0;
    Rectangle buildingNameRect = new Rectangle(403, 520, 174, 26);
    String[] buildings = { "Blacksmith", "HeroGate", "Armory", "TeamTotem", "Barracks", "Sukopon", "MasterObelisk", "SilverHoshipon", "BattleGate",
            "Meden" };

    public HideoutScreenHandler(ScreenData screenData) {
        super(screenData);
    }
    
    public static BufferedImage getSelectedBuilding(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("screens/Hideout/" + imagePath + ".png"));
        } catch (IOException e) {
        }
        return img;
    }
    
    public void navigateToBuilding(String building) throws InterruptedException, IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        for (int index = 0; index < buildings.length; index++) {
            BufferedImage screenImage = getSelectedBuilding(buildings[index]);
            BufferedImage screenCurrent = ScreenIdentifier.getCapture(robot, window, new Rectangle(window.rect.left + buildingNameRect.x, window.rect.top + buildingNameRect.y, buildingNameRect.width, buildingNameRect.height));
            if (ScreenIdentifier.bufferedImagesEqual(screenImage, screenCurrent)) {
                int currentLocation = Arrays.asList(buildings).indexOf(buildings[index]);
                int destination = Arrays.asList(buildings).indexOf(building);
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

    public void execute(Robot robot, int windowID) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (getCurrentAction()) {
            case TOMISSIONSELECT: {
                navigateToBuilding("MasterObelisk");
                break;
            }
            case TOHOME: {
                ScreenHandler.removeActionFromFront();
                break;
            }
            default: {
                
            }
        }
    }

}
