package screenhandlers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.InputController;
import backend.WindowGrab;
import backend.WindowGrab.User32;
import backend.WindowGrab.WindowInfo;
import types.Action;
import types.Input;
import types.ScreenData;
import types.ScreenHandler;
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
    private int iterations;
    private Sequence[] sequences;
    private int catchWidth = 5;
    private int catchHeight = 5;
    private int windowOffset = 16;
    private Rectangle captureRect;
    private WindowInfo window;
    private double start;
    
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
    
    public boolean isInAfterGame() throws IOException {
        BufferedImage screenCapture = robot
                .createScreenCapture(captureRect);
        int[] pixel = screenCapture.getRaster().getPixel(3, 1, new int[3]);
        return (pixel[0] == 53 && pixel[1] == 32 && pixel[2] == 10);
    }

    /*
     * Handles input phase timings.
     */
    public void inputPhase(Sequence sequence) throws InterruptedException {
        long nextFrame = System.currentTimeMillis();
        for (int drumIndex = 0; drumIndex < sequence.getLength(); drumIndex++) {
            nextFrame = nextFrame + (long) (sequence.getTiming(drumIndex) / runSpeed - (Math.max(0,  runSpeed - 2)));
            InputController.processDrumInput(sequence.getDrum(drumIndex), robot);
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
    public boolean resyncPhase() throws InterruptedException, IOException {
        int countdown = 0;
        int attempts = 1;
        Thread.sleep((long) (395 / runSpeed));
        while (countdown != 4) {
            if (isBeat()) {
                if (isInAfterGame()) {
                    System.out.println("Game end detected.");
                    return false;
                }
                if (logging)
                    System.out.println("Doot");
                attempts = 1;
                Thread.sleep((long) (395 / runSpeed) - 3);
                countdown++;
            } else {
                attempts++;
            }
            if (attempts % 100 == 0) {
                System.out.println("Likely window movement, recalculating position.");
                generateCaptureRect();
                InputController.processInput(Input.CROSS, robot);
            }
        }
        return true;
    }
    
    /*
     * Handles gameplay inputs and checks.
     */
    public void gameplay() throws InterruptedException, IOException {
        System.out.println("Entering gameplay phase...");
        start = 1.0 * System.nanoTime() / 1000000000;
        for (int iteration = 0; iteration < this.iterations; iteration++) {
            if (iteration % 15 == 0) {
                generateCaptureRect();
            }
            for (int sequence = 0; sequence < sequences.length; sequence++) {
                if (!resyncPhase()) {
                    iteration = this.iterations;
                    break;
                }
                inputPhase(sequences[sequence]);
            }
            System.out.println("Iteration " + (iteration + 1) + " Complete!");
        }
        System.out.println("Exiting gameplay phase.");
    }

    /*
     * Handles action execution.
     */
    public void execute(Robot robot, int windowID) throws InterruptedException, IOException {
        this.robot = robot;
        this.windowID = windowID;
        switch(getCurrentAction()) {
            case TOBONEDETHBRIGATE: {
                gameplay();
                addActionToFront(Action.TOHOME);
                break;
            }
            default:
                break;
        }
    }

    /*
     * Constructor
     */
    public GameplayScreenHandler(ScreenData screenData, Sequence[] sequence, int iterations) {
        super(screenData);
        this.iterations = iterations;
        this.sequences = sequence;
    }
    

//    MISC CODE
//    BufferedImage screenCapture = robot
//            .createScreenCapture(new Rectangle(window.rect.left + windowOffset, window.rect.bottom - catchHeight - windowOffset, catchWidth, catchHeight));
//    
//    File outputfile = new File("image" + attempts + ".jpg");
//    ImageIO.write(screenCapture, "jpg", outputfile);
}
