package gfx.Snippets;

import java.awt.event.KeyEvent;

public interface KeyRunnable {
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
    public void keyTyped(KeyEvent e);
}
