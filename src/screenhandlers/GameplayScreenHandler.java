package screenhandlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import application.PataponAuto;
import backend.InputController;
import backend.Logger;
import backend.WindowGrab;
import backend.WindowGrab.User32;
import backend.WindowGrab.WindowInfo;
import models.Input;
import models.ScreenData;
import models.ScreenHandler;
import models.ScriptBase;
import models.Sequence;

/**
 * Handles the automatic action input timing and syncing for the gameplay screen.
 * @author Daniel Jin
 * @version 1.1
 */
public class GameplayScreenHandler extends ScreenHandler {
    /*
     * Non-configurable variables
     */
    public static int iterations;
    public static Sequence[] preSequences;
    public static Sequence[] sequences;
    private int catchWidth = 5;
    private int catchHeight = 5;
    private int windowOffset = 16;
    private Rectangle captureRect;
    private WindowInfo window;
    private double runSpeed = 1;
    
    private void generateCaptureRect() {
        window = WindowGrab.getWindowInfo(windowID);
        User32.instance.SetForegroundWindow(window.hwnd);
        captureRect = new Rectangle(window.rect.left + windowOffset, window.rect.bottom - catchHeight - windowOffset, catchWidth, catchHeight);
    }

    /**
     * Scans two pixel point rgb values to check for the flashing border.
     */
    public boolean isBeat() throws IOException {
        BufferedImage screenCapture = robot
                .createScreenCapture(captureRect);
        int[] pixel1 = screenCapture.getRaster().getPixel(3, 1, new int[3]);
        int[] pixel2 = screenCapture.getRaster().getPixel(1, 4, new int[3]);
        return (pixel1[0] > 35 || pixel2[0] > 35);
    }
    

    /**
     * Handles input phase timings.
     */
    public void inputPhase(Sequence sequence) throws InterruptedException {
        long nextFrame = System.currentTimeMillis();
        for (int drumIndex = 0; drumIndex < sequence.getLength(); drumIndex++) {
            nextFrame = nextFrame + (long) (sequence.getTiming(drumIndex) / runSpeed - (Math.max(0,  runSpeed - 2)));
            InputController.processDrumInput(sequence.getDrum(drumIndex), robot);
            Logger.log("Drum: " + sequence.getDrum(drumIndex), 2);
            if (drumIndex != sequence.getLength() - 1)
                Thread.sleep(nextFrame - System.currentTimeMillis());
        }
        return;
    }

    /**
     * Resyncs after the initial input phase.
     */
    public boolean resyncPhase() throws InterruptedException, IOException {
        int countdown = 0;
        int attempts = 1;
        Thread.sleep((long) (395 / runSpeed));
        while (countdown != 4) {
            long nextFrame = System.currentTimeMillis() + (long) (395 / runSpeed) - 5;
            if (isBeat()) {
                attempts = 1;
                Thread.sleep(nextFrame - System.currentTimeMillis());
                countdown++;
            } else {
                attempts++;
            }
            if (attempts % 100 == 0) {
                Logger.log("Likely window movement, recalculating position.", 2);
                if (attempts % 200 == 0 && !isOnScreen(window)) {
                    Logger.log("Exiting gameplay phase.", 2);
                    return false;
                }
                generateCaptureRect();
            }
        }
        return true;
    }
    
    /**
     * Handles gameplay inputs and checks.
     * @return true if iterations expired before completion.
     */
    public boolean gameplay() throws InterruptedException, IOException {
        if (!(preSequences == null)) {
            generateCaptureRect();
            Logger.log("Entering pre-phase sequences...", 2);
            for (int sequence = 0; sequence < preSequences.length; sequence++) {
                if (!resyncPhase()) {
                    return false;
                }
                inputPhase(preSequences[sequence]);
            }
        }
        
        Logger.log("Entering gameplay phase...", 2);
        for (int iteration = 0; iteration < GameplayScreenHandler.iterations; iteration++) {
            if (iteration % 15 == 0) {
                generateCaptureRect();
            }
            for (int sequence = 0; sequence < sequences.length; sequence++) {
                if (!resyncPhase()) {
                    return false;
                }
                inputPhase(sequences[sequence]);
            }
            Logger.log("Sequence iteration " + (iteration + 1) + " Complete.", 2);
        }
        Logger.log("Exiting gameplay phase.", 2);
        return true;
    }

    /**
     * Handles action execution.
     */
    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        this.runSpeed = PataponAuto.runSpeed;
        switch(script.getCurrentAction()) {
            case TOGAMEPLAY: {
                if (gameplay()) {
                    InputController.processInput(Input.START, robot);
                    InputController.processInput(Input.UP, robot);
                    InputController.processInput(Input.CROSS, robot);
                }
                script.removeActionFromFront();
                break;
            }
            case TOHOME: {
                InputController.processInput(Input.START, robot);
                InputController.processInput(Input.UP, robot);
                InputController.processInput(Input.CROSS, robot);
            }
            default:
                break;
        }
    }

    public static void setIterations(int iterations) {
        GameplayScreenHandler.iterations = iterations;
    }

    public static void setSequences(Sequence[] sequences) {
        GameplayScreenHandler.sequences = sequences;
    }

    public static void setPreSequences(Sequence[] preSequences) {
        GameplayScreenHandler.preSequences = preSequences;
    }

    /**
     * Constructor
     */
    public GameplayScreenHandler(ScreenData screenData) {
        super(screenData);
    }
    
}
