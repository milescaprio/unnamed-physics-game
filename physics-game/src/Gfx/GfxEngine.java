package gfx;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import gfx.Snippets.KeyRunnable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Resources.Gfx;

/**
 * A class that can drive 2D graphics for use for this project.
 * Uses a JFrame and JPanel but also has hidden implementation, with GfxObjects.
 */
public class GfxEngine {
    public Camera camera;

    //Manually-handled lock for all key events. If a key event needs to be added during a keyevent, add it to the queue, 
    //because otherwise a deadlock will be formed. 
    //Use waitKeyEventLockTurn() to get the lock, and keyEventLockDone() to release it.
    //Upon the lock being released, all events in the queue will be run.
    private AtomicBoolean keyEventLock = new AtomicBoolean(false);
    private AtomicBoolean isRecursivePEKQ = new AtomicBoolean(false);
    private List<Runnable> PostKeyEventQueue;

    private List<GfxObject> elements;
    private List<KeyRunnable> keyEvents;
    public JFrame frame;
    public GfxPanel panel;
    private Timer frameRefresh;
    public CurrentScreen screenConfig;
    private int framei = 0;

    /**
     * Creates new GfxEngine with empty JFrame and JPanel
     */
    public GfxEngine(Camera camera, String frameTitle) {
        this.camera = camera;
        this.elements = Collections.synchronizedList(new ArrayList<GfxObject>());
        this.frame = new JFrame(frameTitle);
        this.panel = new GfxPanel();
        this.keyEvents = Collections.synchronizedList(new ArrayList<KeyRunnable>());
        this.PostKeyEventQueue = Collections.synchronizedList(new ArrayList<Runnable>());
        this.frameRefresh = new Timer();
        this.screenConfig = new CurrentScreen();
    }
    
    /**
     * Starts the window, using the Camera and Title given at the constructor.
     * If the panel or frame were modified externally before this call, they will
     * remain modified.
     */
    public void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(Gfx.bg);
            frame.setSize(camera.getWidth(), camera.getHeight());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
            frameRefresh.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    panel.repaint();
                    // System.out.printf("Frame:%d Elapsed:%d", framei, System.currentTimeMillis());
                }
            }, 0, 1000 / 30);
        });
    }    

    /**
     * When this function returns, you have the lock. Relock it when you are done.
     */
    private void waitKeyEventLockTurn() {
        while (true) {
            while (keyEventLock.get()); //Wait for turn
            if (keyEventLock.getAndSet(true) == false) {//Make an attempt to get the lock 
                break;
            }
        }
    }

    /**
     * Returns true if the lock is held, false if not.
     * Not to be used for locking, simulatenous locks could be accidentally falsely established.
     */
    public boolean viewKeyEventLock() {
        return keyEventLock.get();
    }

    /**
     * Your runnable should not add to the event queue...
     */
    public void addToPostKeyEventQueue(Runnable runnable) {
        synchronized(PostKeyEventQueue) {
            if (Debug.notify == true) {
                System.out.println("GfxEngine: added to post key event queue");
            }       
            PostKeyEventQueue.add(runnable);
        }
    }

    private void keyEventLockDone() {
        keyEventLock.set(false);
        if (Debug.notify) {
            System.out.println("GfxEngine: key event lock done");
        }
        if (isRecursivePEKQ.getAndSet(true) == false) {
            synchronized(PostKeyEventQueue) {
                for (Runnable runnable : PostKeyEventQueue) {
                    runnable.run();
                }
                if (Debug.notify) {
                    System.out.println("GfxEngine: All runnables in queue run");
                }
                PostKeyEventQueue.clear();
            }
            isRecursivePEKQ.set(false);
        }
    }

    public void debugGfxObjects() {
        for (GfxObject element : elements) {
            System.out.println(element.toString());
        }
    }

    public class GfxPanel extends JPanel implements KeyListener {
        public GfxPanel() {
            super();
            this.setFocusable(true);
            this.requestFocusInWindow();
            this.addKeyListener(this);
        }
        public void paintComponent(Graphics g) {
            try {
                super.paintComponent(g);
                if (Debug.drawing) System.out.println("Repainting");
                if (Debug.drawing) System.out.println("There are " + Integer.valueOf(elements.size()).toString() + " objects to draw");
                for (GfxObject element : elements) {
                    element.draw(camera, g);
                }
            } catch (Exception e) {
                if (Debug.explosive) {
                    System.out.println("Exception in paintComponent");
                    if (Debug.oneError) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                } else {
                    throw e;
                }
            }
        }

        @Override
        public synchronized void keyTyped(KeyEvent e) {
            waitKeyEventLockTurn();
            try {
                for (int i = 0; i < keyEvents.size(); i++) { //Uhm ykw it probablt works
                    KeyRunnable runnable = keyEvents.get(i);
                    if (runnable == null) {
                        if (Debug.notify) {
                            System.out.println("GfxEngine: null keyevent");
                        }
                    } else {
                        if (Debug.notify) {
                            System.out.println("GfxEngine: type event");
                        }
                        runnable.keyTyped(e);
                    }
                }

            } catch (Exception ex) {
                if (Debug.explosive) {
                    System.out.println("Exception in await in keytyped");
                    if (Debug.oneError) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                } else {
                    throw ex;
                }
            }
            keyEventLockDone();
        }

        @Override
        public synchronized void keyPressed(KeyEvent e) {
            waitKeyEventLockTurn();
            try { 
                synchronized(keyEvents) {
                    for (int i = 0; i < keyEvents.size(); i++) { //Uhm ykw it probablt works
                        KeyRunnable runnable = keyEvents.get(i);
                        if (runnable == null) {
                            if (Debug.notify) {
                                System.out.println("GfxEngine: null keyevent");
                            }
                        } else {
                            if (Debug.notify) {
                                System.out.println("GfxEngine: pressing");
                            }
                            runnable.keyPressed(e);
                        }
                    }
                } 
            } catch (Exception ex) {
                if (Debug.explosive) {
                    System.out.println("Exception in await in keypressed");
                    if (Debug.oneError) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                } else {
                    throw ex;
                }
            }
            keyEventLockDone();
        }

        @Override
        public synchronized void keyReleased(KeyEvent e) {
            waitKeyEventLockTurn();
            try {
                for (int i = 0; i < keyEvents.size(); i++) { //Uhm ykw it probablt works
                    KeyRunnable runnable = keyEvents.get(i);
                    if (runnable == null) {
                        if (Debug.notify) {
                            System.out.println("GfxEngine: null keyevent");
                        }
                    } else {
                        if (Debug.notify) {
                            System.out.println("GfxEngine: releasing");
                        }
                        runnable.keyReleased(e);
                    }
                }
            } catch (Exception ex) {
                if (Debug.explosive) {
                    System.out.println("Exception in await in keyreleased");
                    if (Debug.oneError) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                } else {
                    throw ex;
                }
            }
            keyEventLockDone();
        }
    }


    public GfxObjectHandle add_gfx(GfxObject obj) {
        int id = this.elements.size(); //Takes new id, empty old null's are just forgotten
        this.elements.add(obj);
        if (Debug.drawing) System.out.println(elements.toString());
        return new GfxObjectHandle(id);
    }

    public void remove_gfx(GfxObjectHandle handle) {
        this.elements.set(handle.id(), null);
    }

    public void decoration(boolean decorated) {
        SwingUtilities.invokeLater(() -> {
            frame.setUndecorated(!decorated);
        });
    }

    public synchronized KeyEventHandle addKeyEvent(KeyRunnable runnable) {
        waitKeyEventLockTurn();
        this.keyEvents.add(runnable);
        var ret = new KeyEventHandle(this.keyEvents.size() - 1);
        keyEventLockDone();
        return ret;
    }

    public synchronized void removeKeyEvent(KeyEventHandle handle) {
        waitKeyEventLockTurn();
        this.keyEvents.set(handle.id(), null);
        keyEventLockDone();
    }

    public void changeRefreshRate(double hz) {
        SwingUtilities.invokeLater(() -> {
            frameRefresh.cancel();
            frameRefresh = new Timer();
            frameRefresh.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    panel.repaint();
                }
            }, 0, (int)(1000.0 / hz));
        });
    }

    public void adaptRefreshRate() {
        changeRefreshRate(screenConfig.refreshRate);
    }

    public void changeScreenSize(int width, int height) {
        camera.setScreenDimensions(width, height);
        SwingUtilities.invokeLater(() -> {
            frame.setSize(width, height);
        });
    }

    public void fullscreen() {
        changeScreenSize(screenConfig.width, screenConfig.height);
    }

    public void miniscreen() {
        changeScreenSize(screenConfig.width / 2, screenConfig.height / 2);
        //To do
    }

    public class GfxObjectHandle {
        private int id;
        public GfxObjectHandle(int id) {
            this.id = id;
        }
        public int id() {
            return id;
        }
    }

    public class KeyEventHandle {
        private int id;
        public KeyEventHandle(int id) {
            this.id = id;
        }
        public int id() {
            return id;
        }
    }

    public void debugPostKeyEventQueue() {
        System.out.println(PostKeyEventQueue.toString());
    }
}