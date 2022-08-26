package handlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
    private int catchWidth = 5;
    private int catchHeight = 5;
    private int windowOffset = 16;
    private WindowInfo window;
    private double start;
    
    /*
     * runspeed should match PPSSPP's alternative speed setting. 300% => 3.
     * max value for runspeed differs per machine, only set to a speed if ppsspp
     * can reliably keep at least 93% of that speed in fps.
     */
    private double runSpeed = 5.5;
    /*
     * Disable to hide printlns.
     */
    private boolean logging = true;

    /*
     * Scans two pixel point rgb values to check for the flashing border.
     */
    public boolean isBeat() throws IOException {
        BufferedImage screenCapture = robot
                .createScreenCapture(new Rectangle(window.rect.left + windowOffset, window.rect.bottom - catchHeight - windowOffset, catchWidth, catchHeight));
        int[] pixel1 = screenCapture.getRaster().getPixel(3, 1, new int[3]);
        int[] pixel2 = screenCapture.getRaster().getPixel(1, 4, new int[3]);
        return (pixel1[0] > 4 || pixel2[0] > 4);
    }

    /*
     * Handles input phase timings.
     */
    public void inputPhase(Sequence sequence) throws InterruptedException {
        long nextFrame = System.currentTimeMillis();
        for (int drumIndex = 0; drumIndex < sequence.getLength(); drumIndex++) {
            nextFrame = nextFrame + (long) (sequence.getTiming(drumIndex) / runSpeed - (Math.max(0,  runSpeed - 3)));
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
    public void resyncPhase() throws InterruptedException, IOException {
        int countdown = 0;
        int attempts = 1;
        Thread.sleep((long) (450 / runSpeed));
        while (countdown != 4) {
            if (isBeat()) {
                if (logging)
                    System.out.println("Doot");
                attempts = 1;
                Thread.sleep((long) (395 / runSpeed));
                countdown++;
            } else {
                attempts++;
            }
            if (attempts % 100 == 0) {
                
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
    public void execute() throws InterruptedException, IOException {
        start = 1.0 * System.nanoTime() / 1000000000;
        window = WindowGrab.getWindowInfo(windowID);
        for (int iteration = 0; iteration < this.iterations; iteration++) {
            for (int sequence = 0; sequence < sequences.length; sequence++) {
                resyncPhase();
                inputPhase(sequences[sequence]);
                
            }
            System.out.println("Iteration " + (iteration + 1) + " Complete!");
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
