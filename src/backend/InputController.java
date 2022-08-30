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
                TimeController.sleep(30);
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case PON: {
                robot.keyPress(KeyEvent.VK_X);
                TimeController.sleep(30);
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case DON: {
                robot.keyPress(KeyEvent.VK_Z);
                TimeController.sleep(30);
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case CHAKA: {
                robot.keyPress(KeyEvent.VK_S);
                TimeController.sleep(30);
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
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            }
            case DOWN: {
                robot.keyPress(KeyEvent.VK_DOWN);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            }

            case LEFT: {
                robot.keyPress(KeyEvent.VK_LEFT);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            }
            case RIGHT: {
                robot.keyPress(KeyEvent.VK_RIGHT);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            }
            case SQUARE: {
                robot.keyPress(KeyEvent.VK_A);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case CIRCLE: {
                robot.keyPress(KeyEvent.VK_X);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case CROSS: {
                robot.keyPress(KeyEvent.VK_Z);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case TRIANGLE: {
                robot.keyPress(KeyEvent.VK_S);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
            case START: {
                robot.keyPress(KeyEvent.VK_SPACE);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
            }
            case SELECT: {
                robot.keyPress(KeyEvent.VK_V);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_V);
                break;
            }
            case R: {
                robot.keyPress(KeyEvent.VK_W);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_W);
                break;
            }
            case L: {
                robot.keyPress(KeyEvent.VK_Q);
                TimeController.sleep(100);
                robot.keyRelease(KeyEvent.VK_Q);
                break;
            }
            default: {
                Logger.log("Undefined behavior for input: " + input.name(), LogType.ERROR);
            }
        }
        TimeController.sleep(30);
    }

    /**
     * Processes the passed in Input with a custom timeout. Offset cannot go below
     * 10.
     * @param input
     * @param robot
     * @throws InterruptedException
     */
    public static void processInput(Input input, Robot robot, long timeout) throws InterruptedException {
        Logger.log("Input " + input.name() + ".", LogType.INPUT);
        timeout = timeout < 10 ? 10 : timeout;
        switch (input) {
            case UP: {
                robot.keyPress(KeyEvent.VK_UP);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            }
            case DOWN: {
                robot.keyPress(KeyEvent.VK_DOWN);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            }

            case LEFT: {
                robot.keyPress(KeyEvent.VK_LEFT);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            }
            case RIGHT: {
                robot.keyPress(KeyEvent.VK_RIGHT);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            }
            case SQUARE: {
                robot.keyPress(KeyEvent.VK_A);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case CIRCLE: {
                robot.keyPress(KeyEvent.VK_X);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case CROSS: {
                robot.keyPress(KeyEvent.VK_Z);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case TRIANGLE: {
                robot.keyPress(KeyEvent.VK_S);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
            case START: {
                robot.keyPress(KeyEvent.VK_SPACE);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
            }
            case SELECT: {
                robot.keyPress(KeyEvent.VK_V);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_V);
                break;
            }
            case R: {
                robot.keyPress(KeyEvent.VK_W);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_W);
                break;
            }
            case L: {
                robot.keyPress(KeyEvent.VK_Q);
                TimeController.sleep(timeout);
                robot.keyRelease(KeyEvent.VK_Q);
                break;
            }
            default: {
                Logger.log("Undefined behavior for input: " + input.name(), LogType.ERROR);
            }
        }
        TimeController.sleep(30);
    }

}
