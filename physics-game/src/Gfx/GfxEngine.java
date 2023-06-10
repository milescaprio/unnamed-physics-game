package gfx;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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
    private ArrayList<GfxObject> elements;
    private ArrayList<KeyRunnable> keyEvents;
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
        elements = new ArrayList<GfxObject>();
        this.frame = new JFrame(frameTitle);
        this.panel = new GfxPanel();
        this.keyEvents = new ArrayList<KeyRunnable>();
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
            frame.setSize(camera.width, camera.height);
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

    public class GfxPanel extends JPanel implements KeyListener {
        public GfxPanel() {
            super();
            this.setFocusable(true);
            this.requestFocusInWindow();
            this.addKeyListener(this);
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (Debug.drawing) System.out.println("Repainting");
            if (Debug.drawing) System.out.println("There are " + Integer.valueOf(elements.size()).toString() + " objects to draw");
            for (GfxObject element : elements) {
                element.draw(camera, g);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            for (KeyRunnable runnable : keyEvents) {
                runnable.keyTyped(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            for (KeyRunnable runnable : keyEvents) {
                runnable.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            for (KeyRunnable runnable : keyEvents) {
                runnable.keyReleased(e);
            }
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

    public KeyEventHandle addKeyEvent(KeyRunnable runnable) {
        this.keyEvents.add(runnable);
        return new KeyEventHandle(this.keyEvents.size() - 1);
    }

    public void removeKeyEvent(KeyEventHandle handle) {
        this.keyEvents.set(handle.id(), null);
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
        camera.width = width;
        camera.height = height;
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
}