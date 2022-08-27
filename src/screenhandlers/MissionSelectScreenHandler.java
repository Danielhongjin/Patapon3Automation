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
import types.Input;
import types.ScreenData;
import types.ScreenHandler;
import types.ScriptBase;

public class MissionSelectScreenHandler extends ScreenHandler {
    int phase = 0;
    Rectangle missionNameRect = new Rectangle(504, 93, 393, 19);
    String[] missions = { "PataponTrainingGrounds", "DownloadQuest", "FieldofAngryGiants", "ArenaofValor", "CaveofValor", 
            "SnowFieldofSulliedTears", "RacingAlleyofPurity", "TowerofPurity", "PlateauofPompousWings", "RangeofJustice",
            "CastleofJustice", "GreedyMaskJungle", "ArenaofEarnestness", "EstateofEarnestness",
            "BottomlessStomachDesert", "RacingAlleyofRestraint", "LabyrinthofRestraint", "VolcanoZoneoftheLazyDemon", "RangeofAdamance",
            "EvilmassofAdamance", "SavannahofEnviousEyes", "ArenaofTolerance", "TombofTolerance" };

    public MissionSelectScreenHandler(ScreenData screenData) {
        super(screenData);
    }
    
    public static BufferedImage getSelectedMission(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("screens/MissionSelect/" + imagePath + ".png"));
        } catch (IOException e) {
        }
        return img;
    }
    
    public boolean navigateToMission(String mission) throws InterruptedException, IOException {
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        for (int index = 0; index < missions.length; index++) {
            BufferedImage screenImage = getSelectedMission(missions[index]);
            BufferedImage screenCurrent = ScreenIdentifier.getCapture(robot, window, new Rectangle(window.rect.left + missionNameRect.x, window.rect.top + missionNameRect.y, missionNameRect.width, missionNameRect.height));
//            File outputfile = new File("screenshots/" + missions[index] + ".png");
//            ImageIO.write(screenCurrent, "png", outputfile);
//            InputController.processInput(Input.RIGHT, robot);
            if (ScreenIdentifier.bufferedImagesEqual(screenImage, screenCurrent)) {
                int currentLocation = Arrays.asList(missions).indexOf(missions[index]);
                int destination = Arrays.asList(missions).indexOf(mission);
                System.out.println(currentLocation + " " + destination);
                if (destination > currentLocation) {
                    InputController.processInput(Input.RIGHT, robot);
                } else if (destination < currentLocation) {
                    InputController.processInput(Input.LEFT, robot);
                } else {
                    return true;
                }
            }
        }
        return false;
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
            case TOBONEDETHBRIGATE: {
                if (navigateToMission("GreedyMaskJungle")) {
                    InputController.processInput(Input.CROSS, robot);
                    Thread.sleep(250);
                    InputController.processInput(Input.UP, robot);
                    Thread.sleep(250);
                    InputController.processInput(Input.CROSS, robot);
                    script.removeActionFromFront();
                }
                break;
            }
            case TOBONEDETHONTHECLIFF: {
                if (navigateToMission("SavannahofEnviousEyes")) {
                    InputController.processInput(Input.CROSS, robot);
                    Thread.sleep(250);
                    InputController.processInput(Input.UP, robot);
                    Thread.sleep(250);
                    InputController.processInput(Input.CROSS, robot);
                    script.removeActionFromFront();
                }
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

}
