package data.screendata;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import screenhandlers.*;
import types.ScreenData;
import types.ScreenHandler;

public class ScreenDataDB {
    private static ScreenData[] screens = {
            new ScreenData(17, 547, 254, 22, "PostGameStage1", new PostGameStage1ScreenHandler()),
            new ScreenData(15, 548, 269, 22, "PostGameStage2", new  PostGameStage2ScreenHandler())
    };
    
    public static ScreenData getScreen(String name) {
        for (int index = 0; index < screens.length; index++) {
            if (screens[index].getImagePath().equals(name)) return screens[index];
        }
        System.out.println("No screen with that name found");
        return null;
    }

    public static ScreenData[] getScreens() {
        return screens;
    }
    
}
