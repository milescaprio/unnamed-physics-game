package GfxEngine;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Resources.Gfx;
import Snippets.KeyRunnable;
import Resources.Debug;
import Resources.Constants;

/**
 * A class that can drive 2D graphics for use for this project.
 * Uses a JFrame and JPanel but also has hidden implementation, with GfxObjects.
 */
public class GfxEngine {
    public Camera camera;
    private ArrayList<GfxElement> elements;
    private ArrayList<KeyRunnable> keyPressed;
    private ArrayList<KeyRunnable> keyReleased;
    private ArrayList<KeyRunnable> keyTyped; 
    public JFrame frame;
    public GfxPanel panel;
    private Timer frameRefresh;

    /**
     * Creates new GfxEngine with empty JFrame and JPanel
     */
    public GfxEngine(Camera camera) {
        this.camera = camera;
        elements = new ArrayList<GfxElement>();
        this.frame = new JFrame(Constants.title);
        this.panel = new GfxPanel();
        this.keyPressed = new ArrayList<KeyRunnable>();
        this.keyReleased = new ArrayList<KeyRunnable>();
        this.keyTyped = new ArrayList<KeyRunnable>();
        this.frameRefresh = new Timer();
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
            for (GfxElement element : elements) {
                element.obj.draw(camera, g);
                if (Debug.drawing) System.out.println("Drawing " + element.name);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            for (KeyRunnable runnable : keyTyped) {
                runnable.run(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            for (KeyRunnable runnable : keyPressed) {
                runnable.run(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            for (KeyRunnable runnable : keyReleased) {
                runnable.run(e);
            }
        }
    }


    public GfxObjectHandle add_gfx(GfxObject obj, String name) {
        int id = this.elements.size(); //Takes new id, empty old null's are just forgotten
        this.elements.add(new GfxElement(name, id, obj));
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

    public GfxKeyEventHandle addKeyTypedEvent(KeyRunnable runnable) {
        this.keyTyped.add(runnable);
        return new GfxKeyEventHandle(this.keyTyped.size() - 1);
    }

    public GfxKeyEventHandle addKeyPressedEvent(KeyRunnable runnable) {
        this.keyPressed.add(runnable);
        return new GfxKeyEventHandle(this.keyPressed.size() - 1);
    }

    public GfxKeyEventHandle addKeyReleasedEvent(KeyRunnable runnable) {
        this.keyReleased.add(runnable);
        return new GfxKeyEventHandle(this.keyReleased.size() - 1);
    }

    public void removeKeyTypedEvent(GfxKeyEventHandle handle) {
        this.keyTyped.set(handle.id(), null);
    }

    public void removeKeyPressedEvent(GfxKeyEventHandle handle) {
        this.keyPressed.set(handle.id(), null);
    }

    public void removeKeyReleasedEvent(GfxKeyEventHandle handle) {
        this.keyReleased.set(handle.id(), null);
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


    private class GfxElement {
        public String name;
        public int id;
        public GfxObject obj;
    
        public GfxElement(String name, int id, GfxObject obj) {
            this.id = id;
            this.name = name;
            this.obj = obj;
        }
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

    public class GfxKeyEventHandle {
        private int id;
        public GfxKeyEventHandle(int id) {
            this.id = id;
        }
        public int id() {
            return id;
        }
    }
}