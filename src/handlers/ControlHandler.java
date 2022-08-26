package handlers;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import types.Drum;
import types.Input;

/*
 * Translates enums into keyboard input action.
 * @author Daniel Jin
 * @version 1.0
 */
public class ControlHandler {
    public static void processDrumInput(Drum drum, Robot robot) throws InterruptedException {
        switch (drum) {
            case PATA: {
                robot.keyPress(KeyEvent.VK_A);
                Thread.sleep(15);
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case PON: {
                robot.keyPress(KeyEvent.VK_X);
                Thread.sleep(15);
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case DON: {
                robot.keyPress(KeyEvent.VK_Z);
                Thread.sleep(15);
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case CHAKA: {
                robot.keyPress(KeyEvent.VK_S);
                Thread.sleep(15);
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
        }
    }
    
    public static void processInput(Input input, Robot robot) throws InterruptedException {
        switch(input) {
            case UP: {
                robot.keyPress(KeyEvent.VK_UP);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            }
            case DOWN: {
                robot.keyPress(KeyEvent.VK_DOWN);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            }
            
            case LEFT: {
                robot.keyPress(KeyEvent.VK_LEFT);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            }
            case RIGHT: {
                robot.keyPress(KeyEvent.VK_RIGHT);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            }
            case SQUARE: {
                robot.keyPress(KeyEvent.VK_A);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case CIRCLE: {
                robot.keyPress(KeyEvent.VK_X);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case CROSS: {
                robot.keyPress(KeyEvent.VK_Z);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case TRIANGLE: {
                robot.keyPress(KeyEvent.VK_S);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
            case START: {
                robot.keyPress(KeyEvent.VK_SPACE);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
            }
            case SELECT: {
                robot.keyPress(KeyEvent.VK_V);
                Thread.sleep((20));
                robot.keyRelease(KeyEvent.VK_V);
                break;
            }
            default: {
                System.out.println("Undefined behavior for input: " + input.name());
            }
            
        }
    }
}
