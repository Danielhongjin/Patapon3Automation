package handlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import handlers.WindowGrab.User32;
import handlers.WindowGrab.WindowInfo;
import types.Input;
import types.Sequence;

/*
 * Handles the automatic action input timing and syncing for the gameplay screen.
 * @author Daniel Jin
 * @version 1.1
 */
public class GameplayScreenHandler {
    /*
     * Non-configurable variables
     */
    private Robot robot;
    private int windowID;
    private int iterations;
    private Sequence[] sequences;
    private int catchWidth = 40;
    private int catchHeight = 40;
    private int windowOffset = 7;
    private WindowInfo window;
    private double start;
    
    /*
     * runspeed should match PPSSPP's alternative speed setting. 300% => 3.
     */
    private double runSpeed = 3;
    /*
     * Disable to hide printlns.
     */
    private boolean logging = true;

    /*
     * Scans two pixel point rgb values to check for the flashing border.
     */
    public boolean isBeat() {
        BufferedImage screenCapture = robot
                .createScreenCapture(new Rectangle(window.rect.left + windowOffset, window.rect.bottom - catchHeight - windowOffset, catchWidth, catchHeight));
        int[] pixel1 = screenCapture.getRaster().getPixel(9, 20, new int[3]);
        int[] pixel2 = screenCapture.getRaster().getPixel(12, 27, new int[3]);
        return (pixel1[0] > 4 || pixel2[0] > 4);
    }

    /*
     * Handles input phase timings.
     */
    public void inputPhase(Sequence sequence) throws InterruptedException {
        long nextFrame = System.currentTimeMillis();
        for (int drumIndex = 0; drumIndex < sequence.getLength(); drumIndex++) {
            nextFrame = nextFrame + (long) (sequence.getTiming(drumIndex) / runSpeed) + 5;
            ControlHandler.processDrumInput(sequence.getDrum(drumIndex), robot);
            if (logging)
                System.out.println("Drum: " + sequence.getDrum(drumIndex) + "; Time: " + (1.0 * System.nanoTime() / 1000000000 - start));
            if (drumIndex != sequence.getLength() - 1)
                Thread.sleep(nextFrame - System.currentTimeMillis());
        }
        return;
    }

    /*
     * Resyncs after the initial input phase.
     */
    public void resyncPhase() throws InterruptedException {
        int countdown = 0;
        int attempts = 0;
        Thread.sleep((long) (450 / runSpeed));
        while (countdown != 4) {
            if (isBeat()) {
                if (logging)
                    System.out.println("Doot");
                Thread.sleep((long) (395 / runSpeed));
                countdown++;
            } else {
                attempts++;
            }
            if (attempts % 400 == 0) {
                if (attempts == 60)
                    System.out.println("Likely window movement, recalculating position.");
                window = WindowGrab.getWindowInfo(windowID);
                User32.instance.SetForegroundWindow(window.hwnd);
                ControlHandler.processInput(Input.CROSS, robot);
            }
        }
    }

    /*
     * Handles execution of different phases
     */
    public void execute() throws InterruptedException {
        start = 1.0 * System.nanoTime() / 1000000000;

        for (int iteration = 0; iteration < this.iterations; iteration++) {
            window = WindowGrab.getWindowInfo(windowID);
            for (int sequence = 0; sequence < sequences.length; sequence++) {
                inputPhase(sequences[sequence]);
                resyncPhase();
            }
            System.out.println("Iteration " + iteration + " Complete!");
        }
    }

    /*
     * Constructor
     */
    public GameplayScreenHandler(Sequence[] sequence, Robot robot, int windowID, int iterations) {
        super();
        this.robot = robot;
        this.windowID = windowID;
        this.iterations = iterations;
        this.sequences = sequence;
        window = WindowGrab.getWindowInfo(windowID);
    }
}
