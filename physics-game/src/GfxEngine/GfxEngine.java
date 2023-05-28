package GfxEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Resources.Gfx;
import Resources.Debug;
import Resources.Constants;

/**
 * A class that can drive 2D graphics for use for this project.
 */
public class GfxEngine {
    private Camera camera;
    private ArrayList<GfxElement> elements;
    private JFrame frame;
    private GfxPanel panel;
    public class GfxPanel extends JPanel {
        public void paintComponent(Graphics g) {
            if (Debug.drawing) System.out.println("Repainting");
            super.paintComponent(g);
            if (Debug.drawing) System.out.println("There are " + Integer.valueOf(elements.size()).toString() + " objects to draw");
            for (GfxElement element : elements) {
                element.obj.draw(camera, g);
                if (Debug.drawing) System.out.println("Drawing " + element.name);
            }
        }
    }

    public GfxEngine(Camera camera) {
        this.camera = camera;
        elements = new ArrayList<GfxElement>();
    }

    public void init() {
        this.frame = new JFrame(Constants.title);
        this.panel = new GfxPanel();
        // private static final long serialVersionUID = 7148504528835036003L;
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(Gfx.bg);
            frame.setUndecorated(!Constants.isDecorated);
            frame.setSize(camera.width, camera.height);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
    
    public GfxObjectHandle add(GfxObject obj, String name) {
        int id = this.elements.size(); //Takes new id, empty old null's are just forgotten
        this.elements.add(new GfxElement(name, id, obj));
        if (Debug.drawing) System.out.println(elements.toString());
        return new GfxObjectHandle(id);
    }

    private class GfxElement {
        public String name;
        public int id;
        public GfxObject obj;
    
        public GfxElement(String name, int id, GfxObject obj) {
            this.name = name;
            this.id = id;
            this.obj = obj;
        }
    }
}

