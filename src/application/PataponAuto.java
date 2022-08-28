package application;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import data.sequences.AttackSequence;
import data.sequences.MoveSequence;
import models.Action;
import models.ScriptBase;
import models.Sequence;
import screenhandlers.GameplayScreenHandler;

/**
 * Application to automate various aspects of the PSP game Patapon 3. Currently
 * depends on a specific window size setting of 2x and manual code modification.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class PataponAuto {
    public static int screenshotCount = 0;
    /**
     * Runspeed should match what you define as "Alterative Speed" under PPSSPP's
     * graphics setting.
     */
    public static double runSpeed = 3;
    /**
     * [0] = Screen change logging. [1] = Action change logging. [2] = Screen logic
     * logging. [3] = Execution logging.
     */
    public static boolean[] logOptions = { true, true, true, true };

    /**
     * Name of the application running Patapon 3. Ensure that it is in 2x window
     * size and is unobstructed.
     */
    public static String applicationName = "PPSSPP v1.13.1 - UCUS98751 : Patapon™3";

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        Sequence[] sequences = new Sequence[] { new MoveSequence(), new AttackSequence(), new AttackSequence() };
        Sequence[] preSequences = new Sequence[] { new MoveSequence() };
        Thread.sleep(1200);
        ArrayList<Action> actions = new ArrayList<Action>(
                Arrays.asList(Action.TOHOME, Action.TOMISSIONSELECT, Action.TOBONEDETHONTHECLIFF, Action.TOGAMEPLAY, Action.TOHOME));
        GameplayScreenHandler.setSequences(sequences);
        GameplayScreenHandler.setPreSequences(preSequences);
        GameplayScreenHandler.setIterations(50);
        ScriptBase script = new ScriptBase(actions, 20);
        script.run();
    }
}
