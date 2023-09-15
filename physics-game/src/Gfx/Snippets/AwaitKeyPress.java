package gfx.Snippets;
import java.awt.event.KeyEvent;

import gfx.Debug;
import gfx.GfxEngine;
import gfx.GfxEngine.KeyEventHandle;

public class AwaitKeyPress implements Awaiter {
    private int key;
    private boolean pressed;
    private KeyEventHandle kh;
    private Runnable cleanup;
    private GfxEngine listenerLocation;
    public AwaitKeyPress(int key, GfxEngine listenerLocation) {
        this.key = key;
        this.listenerLocation = listenerLocation;
    }

    public void await(Runnable task) {
        if (Debug.notify == true) {
            System.out.println("Adding Awaiter");
        }
        this.kh = listenerLocation.addKeyEvent(new KeyRunnable() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == key) {
                    pressed = true;
                    listenerLocation.addToPostKeyEventQueue(new Runnable() {
                        public void run() {
                            listenerLocation.removeKeyEvent(kh);
                        }
                    });
                    task.run();
                    if (Debug.notify)
                    System.out.println("Awaiter task finished");
                    listenerLocation.debugPostKeyEventQueue();
                    if (Debug.notify)
                    System.out.println("Awaiter task finished 2");
                    if (Debug.notify)
                    System.out.println(listenerLocation.viewKeyEventLock());
                    if (Debug.notify)
                    listenerLocation.debugGfxObjects();
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });
    }
}