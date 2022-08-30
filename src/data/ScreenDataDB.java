package data;

import backend.Logger;
import models.ScreenData;
import models.ScreenHandler;
import screenhandlers.*;
import types.LogType;

/**
 * Database for different ScreenHandlers.
 * @author Daniel Jin
 * @version 1.0
 */
public class ScreenDataDB {
    private static ScreenHandler[] screens = { new PostGameStage1ScreenHandler(new ScreenData(17, 547, 254, 22, "PostGameStage1")),
            new PostGameStage2ScreenHandler(new ScreenData(15, 548, 269, 22, "PostGameStage2")),
            new LoadingScreenHandler(new ScreenData(644, 545, 318, 47, "Loading")), new HideoutScreenHandler(new ScreenData(13, 551, 461, 22, "Hideout")),
            new MissionSelectScreenHandler(new ScreenData(69, 81, 307, 29, "MissionSelect")),
            new PreGameScreenHandler(new ScreenData(15, 548, 212, 48, "PreGame")),
            new SetSkillDeployScreenHandler(new ScreenData(341, 189, 290, 66, "SetSkillDeploy")),
            new DeployScreenHandler(new ScreenData(398, 194, 174, 39, "Deploy")), new GameplayScreenHandler(new ScreenData(770, 550, 164, 25, "Gameplay")), 
            new ArmoryScreenHandler(new ScreenData(491, 107, 128, 42, "Armory"))};

    public static ScreenHandler getScreen(String name) {
        for (int index = 0; index < screens.length; index++) {
            if (screens[index].getScreenData().getImagePath().equals(name))
                return screens[index];
        }

        Logger.log("No screen with that name found", LogType.ERROR);
        return null;
    }

    public static ScreenHandler[] getScreens() {
        return screens;
    }

}
