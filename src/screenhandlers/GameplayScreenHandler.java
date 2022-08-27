package screenhandlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import application.PataponAuto;
import backend.InputController;
import backend.WindowGrab;
import backend.WindowGrab.User32;
import backend.WindowGrab.WindowInfo;
import types.ScreenData;
import types.ScreenHandler;
import types.ScriptBase;
import types.Sequence;

/*
 * Handles the automatic action input timing and syncing for the gameplay screen.
 * @author Daniel Jin
 * @version 1.1
 */
public class GameplayScreenHandler extends ScreenHandler {
    /*
     * Non-configurable variables
     */
    public static int iterations;
    public static Sequence[] sequences;
    private int catchWidth = 5;
    private int catchHeight = 5;
    private int windowOffset = 16;
    private Rectangle captureRect;
    private WindowInfo window;
    private double start;
    private double runSpeed = 1;
    
    private void generateCaptureRect() {
        window = WindowGrab.getWindowInfo(windowID);
        User32.instance.SetForegroundWindow(window.hwnd);
        captureRect = new Rectangle(window.rect.left + windowOffset, window.rect.bottom - catchHeight - windowOffset, catchWidth, catchHeight);
    }

    /*
     * Scans two pixel point rgb values to check for the flashing border.
     */
    public boolean isBeat() throws IOException {
        BufferedImage screenCapture = robot
                .createScreenCapture(captureRect);
        int[] pixel1 = screenCapture.getRaster().getPixel(3, 1, new int[3]);
        int[] pixel2 = screenCapture.getRaster().getPixel(1, 4, new int[3]);
        return (pixel1[0] > 35 || pixel2[0] > 35);
    }
    

    /*
     * Handles input phase timings.
     */
    public void inputPhase(Sequence sequence) throws InterruptedException {
        long nextFrame = System.currentTimeMillis();
        for (int drumIndex = 0; drumIndex < sequence.getLength(); drumIndex++) {
            nextFrame = nextFrame + (long) (sequence.getTiming(drumIndex) / runSpeed - (Math.max(0,  runSpeed - 2)));
            InputController.processDrumInput(sequence.getDrum(drumIndex), robot);
            if (logOptions[2])
                System.out.println("[ScreenAction]\tDrum: " + sequence.getDrum(drumIndex) + "; Time: " + (1.0 * System.nanoTime() / 1000000000 - start));
            if (drumIndex != sequence.getLength() - 1)
                Thread.sleep(nextFrame - System.currentTimeMillis());
        }
        return;
    }

    /*
     * Resyncs after the initial input phase.
     */
    public boolean resyncPhase() throws InterruptedException, IOException {
        int countdown = 0;
        int attempts = 1;
        Thread.sleep((long) (395 / runSpeed));
        while (countdown != 4) {
            long nextFrame = System.currentTimeMillis() + (long) (395 / runSpeed) - 5;
            if (isBeat()) {
                if (logOptions[2]) System.out.println("[ScreenAction]\tDoot");
                attempts = 1;
                Thread.sleep(nextFrame - System.currentTimeMillis());
                countdown++;
            } else {
                attempts++;
            }
            if (attempts % 100 == 0) {
                if (logOptions[2]) System.out.println("[ScreenAction]\tLikely window movement, recalculating position.");
                if (attempts % 200 == 0 && !isOnScreen(window)) {
                    System.out.println("Game end detected.");
                    return false;
                }
                generateCaptureRect();
            }
        }
        return true;
    }
    
    /*
     * Handles gameplay inputs and checks.
     */
    public void gameplay() throws InterruptedException, IOException {
        if (logOptions[2]) System.out.println("[ScreenAction]\tEntering gameplay phase...");
        start = 1.0 * System.nanoTime() / 1000000000;
        for (int iteration = 0; iteration < GameplayScreenHandler.iterations; iteration++) {
            if (iteration % 15 == 0) {
                generateCaptureRect();
            }
            for (int sequence = 0; sequence < sequences.length; sequence++) {
                if (!resyncPhase()) {
                    iteration = GameplayScreenHandler.iterations;
                    break;
                }
                inputPhase(sequences[sequence]);
            }
            if (logOptions[2]) System.out.println("[ScreenAction]\tSequence iteration " + (iteration + 1) + " Complete.");
        }
        if (logOptions[2]) System.out.println("[ScreenAction]\tExiting gameplay phase.");
    }

    /*
     * Handles action execution.
     */
    @Override
    public void execute(Robot robot, int windowID, ScriptBase script) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        this.runSpeed = PataponAuto.runSpeed;
        switch(script.getCurrentAction()) {
            case TOGAMEPLAY: {
                gameplay();
                script.removeActionFromFront();
                break;
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

    /*
     * Constructor
     */
    public GameplayScreenHandler(ScreenData screenData) {
        super(screenData);
    }
    
}
