package screenhandlers;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import backend.InputController;
import backend.Logger;
import backend.ScreenIdentifier;
import backend.TimeController;
import backend.WindowGrab;
import backend.WindowGrab.WindowInfo;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;
import types.Action;
import types.Input;
import types.LogType;

/**
 * ScreenHandler for the armory screen.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class ArmoryScreenHandler extends ScreenHandler {
    static int z = 0;
    boolean[] targets;
    /**
     * Differen scan regions.
     */
    Rectangle disbandPopupScan = new Rectangle(379, 276, 209, 15);
    Rectangle nonEnchantedScan = new Rectangle(507, 239, 14, 20);
    Rectangle enchantmentScan = new Rectangle(547, 237, 400, 24);
    Rectangle weaponTabScan = new Rectangle(197, 75, 89, 21);
    /**
     * Differen scan images. Requires at least 3 each for different subpixel arrangements.
     */
    BufferedImage[] FL = { ScreenIdentifier.getImage("Armory/EnchantmentFL"), ScreenIdentifier.getImage("Armory/EnchantmentFL2"),
            ScreenIdentifier.getImage("Armory/EnchantmentFL3") };
    BufferedImage[] PO = { ScreenIdentifier.getImage("Armory/EnchantmentPO") };
    BufferedImage[] ST = { ScreenIdentifier.getImage("Armory/EnchantmentST"), ScreenIdentifier.getImage("Armory/EnchantmentST2"),
            ScreenIdentifier.getImage("Armory/EnchantmentST3") };
    BufferedImage[] HP = { ScreenIdentifier.getImage("Armory/EnchantmentHP"), ScreenIdentifier.getImage("Armory/EnchantmentHP2"),
            ScreenIdentifier.getImage("Armory/EnchantmentHP3") };
    BufferedImage[] AR = { ScreenIdentifier.getImage("Armory/EnchantmentAR"), ScreenIdentifier.getImage("Armory/EnchantmentAR2"),
            ScreenIdentifier.getImage("Armory/EnchantmentAR3") };
    BufferedImage[] bracketPermutations = { ScreenIdentifier.getImage("Armory/EnchantmentLeftBracket"),
            ScreenIdentifier.getImage("Armory/EnchantmentLeftBracket2") };

    public ArmoryScreenHandler(ScreenData screenData) {
        super(screenData);
    }

    /**
     * Checks whether the disband window has opened or not.
     * @param window
     * @return
     * @throws IOException
     */
    public boolean disbandPopupVisible(WindowInfo window) throws IOException {
        BufferedImage screenImage = ScreenIdentifier.getImage("Armory/DisbandPopup");
        BufferedImage currentImage = ScreenIdentifier.getCapture(robot, window, disbandPopupScan);
        if (ScreenIdentifier.bufferedImagesEqual(screenImage, currentImage))
            return true;
        return false;

    }

    /**
     * Uses the lightness value to guess whether it is a normal item or not.
     * @param window
     * @return
     */
    public boolean isImageNonEnchanted(WindowInfo window) {
        BufferedImage image = ScreenIdentifier.getCapture(robot, window, nonEnchantedScan);
        long rgb = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x, y));
                rgb += pixel.getRed();
                rgb += pixel.getGreen();
                rgb += pixel.getBlue();
            }
        }
        rgb /= 3;
        rgb /= (image.getWidth() * image.getHeight());
        if (rgb < 150) {
            return true;
        }
        return false;
    }

    /**
     * Uses a series of image checks to return which enchantment is selected.
     * @param window
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public int getImageEnchantment(WindowInfo window) throws IOException, InterruptedException {
        BufferedImage image = ScreenIdentifier.getCapture(robot, window, enchantmentScan);
        for (int x = 0; x < image.getWidth() - 40; x++) {
            BufferedImage subImage = image.getSubimage(x, 0, 12, 24);
            double likeness = ScreenIdentifier.getImageRelatedness(subImage, bracketPermutations[0]);
            double likeness2 = ScreenIdentifier.getImageRelatedness(subImage, bracketPermutations[1]);
            if (likeness > 0.8 || likeness2 > 0.8) {
                subImage = image.getSubimage(x + 12, 0, 27, 24);
                for (int index = 0; index < FL.length; index++) {
                    if (ScreenIdentifier.getImageRelatedness(subImage, FL[index]) > 0.8) {
                        return 1;
                    }
                }
                for (int index = 0; index < PO.length; index++) {
                    if (ScreenIdentifier.getImageRelatedness(subImage, PO[index]) > 0.8) {
                        return 2;
                    }
                }
                for (int index = 0; index < ST.length; index++) {
                    if (ScreenIdentifier.getImageRelatedness(subImage, ST[index]) > 0.8) {
                        return 3;
                    }
                }
                for (int index = 0; index < HP.length; index++) {
                    if (ScreenIdentifier.getImageRelatedness(subImage, HP[index]) > 0.8) {
                        return 7;
                    }
                }
                for (int index = 0; index < AR.length; index++) {
                    if (ScreenIdentifier.getImageRelatedness(subImage, AR[index]) > 0.8) {
                        return 8;
                    }
                }

            }
        }
        return -1;
    }

    /**
     * Handles selling of items.
     * @param targets
     * @throws InterruptedException
     * @throws IOException
     */
    private void sell(boolean[] targets) throws InterruptedException, IOException {
        for (int index = 0; index < 250; index++) {
            boolean marked = false;
            WindowInfo window = WindowGrab.getWindowInfo(windowID);
            if (isImageNonEnchanted(window)) {
                if (targets[0])
                    marked = true;
            } else {
                int enchantment = getImageEnchantment(window);
                if (enchantment != -1 && targets[enchantment])
                    marked = true;
            }
            if (marked) {
                InputController.processInput(Input.CROSS, robot);
                TimeController.sleep(100);
                if (disbandPopupVisible(window)) {
                    InputController.processInput(Input.UP, robot);
                    InputController.processInput(Input.CROSS, robot);
                    z++;
                    index++;
                } else {
                    InputController.processInput(Input.RIGHT, robot);
                }
            } else {
                InputController.processInput(Input.RIGHT, robot);
            }
            TimeController.sleep(75);

        }

    }

    /**
     * Scrolls through weapon and armor pages to sell items. Has a value that counts down from 250
     * for each item scanned. Items sold will decrease the value by two.
     * @param script
     * @throws InterruptedException
     * @throws IOException
     */
    private void sellPhase(ScriptBase script) throws InterruptedException, IOException {
        z = 0;
        WindowInfo window = WindowGrab.getWindowInfo(windowID);
        targets = new boolean[] { false, false, false, false, false, false, false, false, false, false };
        boolean finished = false;
        while (!finished) {
            if (script.isEmpty()) {
                script.addActionToFront(Action.TOHOME);
            }
            switch (script.getCurrentAction()) {
                case SELLALLNONENCHANTED: {
                    targets[0] = true;
                    script.removeActionFromFront();
                    break;
                }
                case SELLALLFL: {
                    targets[1] = true;
                    script.removeActionFromFront();
                    break;
                }
                case SELLALLST: {
                    targets[2] = true;
                    script.removeActionFromFront();
                    break;
                }
                case SELLALLPO: {
                    targets[3] = true;
                    script.removeActionFromFront();
                    break;
                }
                case SELLALLHP: {
                    targets[7] = true;
                    script.removeActionFromFront();
                    break;
                }
                case SELLALLAR: {
                    targets[8] = true;
                    script.removeActionFromFront();
                    break;
                }
                case SELLALL: {
                    for (int index = 0; index < targets.length; index++) {
                        targets[index] = true;
                    }
                    script.removeActionFromFront();
                    break;
                }
                default: {
                    finished = true;
                }
            }
        }
        finished = false;
        while (!finished) {
            InputController.processInput(Input.R, robot);
            TimeController.sleep(100);
            if (ScreenIdentifier.bufferedImagesEqual(ScreenIdentifier.getCapture(robot, window, weaponTabScan),
                    ScreenIdentifier.getImage("Armory/WeaponTabScan"))) {
                finished = true;
            }
        }
        Logger.log("Selling weapons.", LogType.SCREENLOGIC);
        sell(targets);
        Logger.log("Selling armor.", LogType.SCREENLOGIC);
        InputController.processInput(Input.R, robot);
        sell(targets);
        Logger.log(z + " items have been sold.", LogType.SCREENLOGIC);
    }

    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;

        switch (script.getCurrentAction()) {
            case SELLALLFL:
            case SELLALLST:
            case SELLALLPO:
            case SELLALLHP:
            case SELLALLAR:
            case SELLALL:
            case SELLALLNONENCHANTED: {
                sellPhase(script);
                break;
            }
            case TOHOME: {
                InputController.processInput(Input.CIRCLE, robot);
                break;
            }
            case TOARMORY: {
                script.removeActionFromFront();
                break;
            }
            default: {
                script.addActionToFront(Action.TOHOME);
            }
        }
    }

}
