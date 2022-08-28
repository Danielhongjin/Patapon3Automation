package models;

import java.awt.Rectangle;
import backend.WindowGrab.WindowInfo;

/**
 * Contains data used for identifying a ScreenHandler.
 * @author Daniel Jin
 * @version 1.0
 */
public class ScreenData {
    private int x;
    private int y;
    private int width;
    private int height;
    private String imagePath;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public Rectangle getRect(WindowInfo window) {
        return new Rectangle(window.rect.left + x, window.rect.top + y, width, height);
    }

    

    public ScreenData(int x, int y, int width, int height, String imagePath) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
    }
    
    
    

}
