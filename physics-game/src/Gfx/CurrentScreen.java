package gfx;
import java.awt.GraphicsEnvironment;

/**
 * An object, that when constructed, gets the current screen information for
 * rendering purposes. Contains methods for quick operations on the screen.
 */
public class CurrentScreen {
    public int refreshRate;
    public int width;
    public int height;
    private int centerX;
    private int centerY;
    /**
     * Constructs a new ScreenConfig object, which contains information about the
     * current screen.
     */
    public CurrentScreen() {
        this.refreshRate = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getDefaultScreenDevice() 
        .getDisplayMode()
        .getRefreshRate();

        this.width = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getDefaultScreenDevice()
        .getDisplayMode()
        .getWidth();

        this.height = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getDefaultScreenDevice()
        .getDisplayMode()
        .getHeight();

        this.centerX = width / 2;
        this.centerY = height / 2;
    }

    public int getWindowCenterCornerX(int windowWidth) {
        return centerX - windowWidth / 2;
    }
    
    public int getWindowCenterCornerY(int windowHeight) {
        return centerY - windowHeight / 2;
    }

}
