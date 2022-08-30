package backend;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import application.PataponAuto;
import types.Drum;
import types.Input;
import types.LogType;

/**
 * Translates enums into keyboard input action.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class InputController {
    public static void processDrumInput(Drum drum, Robot robot) throws InterruptedException {
        Logger.log("Drum: " + drum.name(), LogType.INPUT);
        switch (drum) {
            case PATA: {
                robot.keyPress(KeyEvent.VK_A);
                Thread.sleep(30);
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case PON: {
                robot.keyPress(KeyEvent.VK_X);
                Thread.sleep(30);
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case DON: {
                robot.keyPress(KeyEvent.VK_Z);
                Thread.sleep(30);
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case CHAKA: {
                robot.keyPress(KeyEvent.VK_S);
                Thread.sleep(30);
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
        }
    }

    /**
     * Processes the passed in Input.
     * 
     * @param input
     * @param robot
     * @throws InterruptedException
     */
    public static void processInput(Input input, Robot robot) throws InterruptedException {
        Logger.log("Input " + input.name() + ".", LogType.INPUT);
        switch (input) {
            case UP: {
                robot.keyPress(KeyEvent.VK_UP);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            }
            case DOWN: {
                robot.keyPress(KeyEvent.VK_DOWN);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            }

            case LEFT: {
                robot.keyPress(KeyEvent.VK_LEFT);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            }
            case RIGHT: {
                robot.keyPress(KeyEvent.VK_RIGHT);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            }
            case SQUARE: {
                robot.keyPress(KeyEvent.VK_A);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case CIRCLE: {
                robot.keyPress(KeyEvent.VK_X);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case CROSS: {
                robot.keyPress(KeyEvent.VK_Z);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case TRIANGLE: {
                robot.keyPress(KeyEvent.VK_S);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
            case START: {
                robot.keyPress(KeyEvent.VK_SPACE);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
            }
            case SELECT: {
                robot.keyPress(KeyEvent.VK_V);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_V);
                break;
            }
            case R: {
                robot.keyPress(KeyEvent.VK_W);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_W);
                break;
            }
            case L: {
                robot.keyPress(KeyEvent.VK_Q);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_Q);
                break;
            }
            default: {
                Logger.log("Undefined behavior for input: " + input.name(), LogType.ERROR);
            }
        }
        Thread.sleep((long) (30 / PataponAuto.runSpeed));
    }

    /**
     * Processes the passed in Input with a custom timeout. Offset cannot go below
     * 10.
     * @param input
     * @param robot
     * @throws InterruptedException
     */
    public static void processInput(Input input, Robot robot, long offset) throws InterruptedException {
        Logger.log("Input " + input.name() + ".", LogType.INPUT);
        offset = offset < 10 ? 10 : offset;
        switch (input) {
            case UP: {
                robot.keyPress(KeyEvent.VK_UP);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            }
            case DOWN: {
                robot.keyPress(KeyEvent.VK_DOWN);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            }

            case LEFT: {
                robot.keyPress(KeyEvent.VK_LEFT);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            }
            case RIGHT: {
                robot.keyPress(KeyEvent.VK_RIGHT);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            }
            case SQUARE: {
                robot.keyPress(KeyEvent.VK_A);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case CIRCLE: {
                robot.keyPress(KeyEvent.VK_X);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case CROSS: {
                robot.keyPress(KeyEvent.VK_Z);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case TRIANGLE: {
                robot.keyPress(KeyEvent.VK_S);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
            case START: {
                robot.keyPress(KeyEvent.VK_SPACE);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
            }
            case SELECT: {
                robot.keyPress(KeyEvent.VK_V);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_V);
                break;
            }
            case R: {
                robot.keyPress(KeyEvent.VK_W);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_W);
                break;
            }
            case L: {
                robot.keyPress(KeyEvent.VK_Q);
                Thread.sleep((long) (offset / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_Q);
                break;
            }
            default: {
                Logger.log("Undefined behavior for input: " + input.name(), LogType.ERROR);
            }
        }
        Thread.sleep((long) (30 / PataponAuto.runSpeed));
    }

}
