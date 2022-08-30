package backend;

import application.PataponAuto;

/**
 * Controls time itself.
 * 
 * @author Daniel Jin
 * @version 1.0
 */
public class TimeController {
    /**
     * Performs a Thread.sleep with timeout modified by runspeed.
     * @param timeout
     * @throws InterruptedException
     */
   public static void sleep(long timeout) throws InterruptedException {
       Thread.sleep(Math.max((long) (timeout / PataponAuto.runSpeed), 0));
   }
   
   /**
    * Performs a Thread.sleep with timeout modified by runspeed.
    * @param timeout
    * @throws InterruptedException
    */
   public static void sleep(Double timeout) throws InterruptedException {
       Thread.sleep(Math.max((long) (timeout / PataponAuto.runSpeed), 0));
   }

}
