package types;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.WindowGrab.WindowInfo;

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
