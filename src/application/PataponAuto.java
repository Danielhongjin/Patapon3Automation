package application;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import data.sequences.AttackSequence;
import data.sequences.MoveSequence;
import models.ScriptBase;
import models.Sequence;
import screenhandlers.GameplayScreenHandler;
import types.Action;

/**
 * Application to automate various aspects of the PSP game Patapon 3. Currently
 * depends on a specific window size setting of 2x and manual code modification.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class PataponAuto {
    public static int IDCounter = 1;
    public static int screenshotCount = 0;
    /**
     * Runspeed should match what you define as "Alternative Speed" under PPSSPP's
     * graphics setting.
     */
    public static double runSpeed = 4.5;
    /**
     * Turn false to turn any particular type of logging off.
     */
    public static boolean[] logOptions = {
            // Errors.
            true,
            // Execution.
            true,
            // Screen Changes.
            true,
            // Action Changes.
            true,
            // Screen Logic.
            true,
            // Inputs.
            false };

    /**
     * Name of the application running Patapon 3. Ensure that it is in 2x window
     * size and is unobstructed.
     */
    public static String applicationName = "PPSSPP v1.13.1 - UCUS98751 : Patapon™3";

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        Sequence[] sequences = new Sequence[] { new MoveSequence(), new AttackSequence(), new AttackSequence() };
        Sequence[] preSequences = new Sequence[] { new MoveSequence() };
        Thread.sleep(1200);
        ArrayList<Action> startup = new ArrayList<Action>(Arrays.asList(Action.TOHOME, Action.TOARMORY, Action.SELLALL));
        ArrayList<Action> actions = new ArrayList<Action>(Arrays.asList(Action.TOHOME, Action.TOMISSIONSELECT, Action.TOUBERHEROESNEVERREST, Action.TOGAMEPLAY,
                Action.TOMISSIONSELECT, Action.TOBONEDETHBRIGATE, Action.TOGAMEPLAY, Action.TOMISSIONSELECT, Action.TOWIPETHEGRINOFFTHEGARGOYLE,
                Action.TOGAMEPLAY, Action.TOMISSIONSELECT, Action.TOBONEDETHONTHECLIFF, Action.TOGAMEPLAY));
        ArrayList<Action> teardown = new ArrayList<Action>(Arrays.asList(Action.TOARMORY, Action.SELLALL));
        GameplayScreenHandler.setSequences(sequences);
        GameplayScreenHandler.setPreSequences(preSequences);
        GameplayScreenHandler.setIterations(50);
        ScriptBase script = new ScriptBase(IDCounter++, "Generic", startup, actions, teardown, 1, 1);
        script.run();

    }
}
