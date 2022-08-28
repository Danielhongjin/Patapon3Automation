package backend;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import application.PataponAuto;
import models.Drum;
import models.Input;

/**
 * Translates enums into keyboard input action.
 * @author Daniel Jin
 * @version 1.0
 */
public class InputController {
    public static void processDrumInput(Drum drum, Robot robot) throws InterruptedException {
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
    
    public static void processInput(Input input, Robot robot) throws InterruptedException {
        switch(input) {
            case UP: {
                Logger.log("Input UP.", 2); 
                robot.keyPress(KeyEvent.VK_UP);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            }
            case DOWN: {
                Logger.log("Input DOWN.", 2); 
                robot.keyPress(KeyEvent.VK_DOWN);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            }
            
            case LEFT: {
                Logger.log("Input LEFT.", 2); 
                robot.keyPress(KeyEvent.VK_LEFT);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            }
            case RIGHT: {
                Logger.log("Input RIGHT.", 2); 
                robot.keyPress(KeyEvent.VK_RIGHT);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            }
            case SQUARE: {
                Logger.log("Input SQUARE.", 2); 
                robot.keyPress(KeyEvent.VK_A);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_A);
                break;
            }
            case CIRCLE: {
                Logger.log("Input CIRCLE.", 2); 
                robot.keyPress(KeyEvent.VK_X);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_X);
                break;
            }
            case CROSS: {
                Logger.log("Input CROSS.", 2); 
                robot.keyPress(KeyEvent.VK_Z);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_Z);
                break;
            }
            case TRIANGLE: {
                Logger.log("Input TRIANGLE.", 2); 
                robot.keyPress(KeyEvent.VK_S);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_S);
                break;
            }
            case START: {
                Logger.log("Input START.", 2); 
                robot.keyPress(KeyEvent.VK_SPACE);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
            }
            case SELECT: {
                Logger.log("Input SELECT.", 2); 
                robot.keyPress(KeyEvent.VK_V);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_V);
                break;
            }
            case R: {
                Logger.log("Input SELECT.", 2); 
                robot.keyPress(KeyEvent.VK_W);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_W);
                break;
            }
            case L: {
                Logger.log("Input SELECT.", 2); 
                robot.keyPress(KeyEvent.VK_Q);
                Thread.sleep((long) (100 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_Q);
                break;
            }
            default: {
                Logger.log("Undefined behavior for input: " + input.name(), 2);
            }
            
        }
    }
    
    public static void processShoulderInput(Input input, Robot robot) throws InterruptedException {
        switch(input) {
            case R: {
                Logger.log("Input SELECT.", 2); 
                robot.keyPress(KeyEvent.VK_W);
                Thread.sleep((long) (40 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_W);
                break;
            }
            case L: {
                Logger.log("Input SELECT.", 2); 
                robot.keyPress(KeyEvent.VK_Q);
                Thread.sleep((long) (40 / PataponAuto.runSpeed));
                robot.keyRelease(KeyEvent.VK_Q);
                break;
            }
            default: {
                Logger.log("Undefined behavior for input: " + input.name(), 2);
            }
            
        }
    }
    
    
}
