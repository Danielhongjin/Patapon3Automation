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
import backend.TimeController;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;
import types.Input;

/**
 * ScreenHandler for the mission select screen.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class MissionSelectScreenHandler extends ScreenHandler {
    int phase = 0;
    Rectangle missionNameRect = new Rectangle(504, 93, 393, 19);
    String[] missions = { "PataponTrainingGrounds", "DownloadQuest", "FieldofAngryGiants", "ArenaofValor", "CaveofValor", "SnowFieldofSulliedTears",
            "RacingAlleyofPurity", "TowerofPurity", "PlateauofPompousWings", "RangeofJustice", "CastleofJustice", "GreedyMaskJungle", "ArenaofEarnestness",
            "EstateofEarnestness", "BottomlessStomachDesert", "RacingAlleyofRestraint", "LabyrinthofRestraint", "VolcanoZoneoftheLazyDemon", "RangeofAdamance",
            "EvilmassofAdamance", "SavannahofEnviousEyes", "ArenaofTolerance", "TombofTolerance" };

    /**
     * Finds image for specified building.
     * 
     * @param imagePath
     * @return
     */
    public static BufferedImage getMissionImage(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("screens/MissionSelect/" + imagePath + ".png"));
        } catch (IOException e) {
        }
        return img;
    }

    /**
     * Checks for current selected mission and decides which input to use
     * accordingly.
     * 
     * @param target the mission being aimed for.
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean navigateToMission(String target) throws InterruptedException, IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        for (int index = 0; index < missions.length; index++) {
            BufferedImage screenImage = getMissionImage(missions[index]);
            BufferedImage screenCurrent = ScreenIdentifier.getCapture(robot, window, missionNameRect);
            if (ScreenIdentifier.bufferedImagesEqual(screenImage, screenCurrent)) {
                int currentLocation = Arrays.asList(missions).indexOf(missions[index]);
                int destination = Arrays.asList(missions).indexOf(target);
                if (destination > currentLocation) {
                    InputController.processInput(Input.RIGHT, robot);
                } else if (destination < currentLocation) {
                    InputController.processInput(Input.LEFT, robot);
                } else {
                    TimeController.sleep(75);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void navigate(ScriptBase script, String locale, int menuDown, int navigateDirection) throws InterruptedException, IOException {
        if (navigateToMission(locale)) {
            if (navigateDirection > 0) {
                InputController.processInput(Input.UP, robot);
            } else  if (navigateDirection < 0) {
                InputController.processInput(Input.DOWN, robot);
            }
            InputController.processInput(Input.CROSS, robot);
            for (int index = 0; index < menuDown; index++) {
                InputController.processInput(Input.DOWN, robot);
            }
            InputController.processInput(Input.CROSS, robot);
            script.removeActionFromFront();
        }
        TimeController.sleep(500);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch (script.getCurrentAction()) {
            case TOMISSIONSELECT: {
                script.removeActionFromFront();
                break;
            }
            case TOUBERHEROESNEVERREST: {
                navigate(script, "ArenaofTolerance", 1, 0);
                break;
            }
            case TOWIPETHEGRINOFFTHEGARGOYLE: {
                navigate(script, "SavannahofEnviousEyes", 2, 0);
                break;
            }
            case TOBONEDETHBRIGATE: {
                navigate(script, "GreedyMaskJungle", 3, 0);
                break;
            }
            case TOBONEDETHONTHECLIFF: {
                navigate(script, "SavannahofEnviousEyes", 3, 0);
                break;
            }
            case TOHOME: {
                InputController.processInput(Input.CIRCLE, robot);
                break;
            }
            default: {

            }
        }
    }

    public MissionSelectScreenHandler(ScreenData screenData) {
        super(screenData);
    }
}
