import GfxEngine.Camera.Point;
import Resources.Constants;
import Snippets.KeyRunnable;
import Snippets.UnitTest;

import java.awt.event.KeyEvent;

import GfxEngine.Camera;
import GfxEngine.GfxEngine;
import GfxEngine.GfxFigure;
import GfxEngine.GfxEngine.GfxObjectHandle;
import GfxEngine.RGB;
import GfxEngine.SolidCircle;
import GfxEngine.SolidRectangle;

//
// Hey guys this is the head of testing the program
// Skim the code in this file and run it
//
//

/**
 * The main class for testing code
 */
public class Dev {
    public static void main(String[] args) {
        GfxUnitTests();
        GfxGraphicsTest();
    }
    

    //Unit Tests 1
    public static void GfxUnitTests() {
        SolidRectangle rect = new SolidRectangle(0, 0, 100, 100, new RGB(255, 60, 30));
        SolidCircle circ = new SolidCircle(0,0,50, new RGB(255, 0, 255));
        SolidCircle circ1 = new SolidCircle(120,90,30, new RGB(255,255,0));
        SolidCircle circ2 = new SolidCircle(120,110,30, new RGB(255,255,0));
        SolidCircle circ3 = new SolidCircle(120,-110,30, new RGB(255,255,0));
        SolidCircle circx2 = new SolidCircle(120,-135,30, new RGB(255,255,0));
        SolidCircle circx3 = new SolidCircle(-149,149,50, new RGB(255,255,0));
        {
            UnitTest.test(rect.containsPoint(new Point(50, 50)), true, "center");
            UnitTest.test(rect.containsPoint(new Point(30, -110)), false, "out");
            UnitTest.test(rect.containsPoint(new Point(30, 29)), true, "lower");
            UnitTest.test(rect.containsPoint(new Point(70, 60)), true, "higher");
        }
        {
            UnitTest.test(circ.containsPoint(new Point(0, 0)), true, "center");
            UnitTest.test(circ.containsPoint(new Point(0, 49)), true, "inedge right");
            UnitTest.test(circ.containsPoint(new Point(0, 51)), false, "rightout");
            UnitTest.test(circ.containsPoint(new Point(0, -49)), true, "inedge");
            UnitTest.test(circ.containsPoint(new Point(0, -51)), false, "leftout");
            UnitTest.test(circ.containsPoint(new Point(30, -30)), true, "topleft in");
        }
        {
            UnitTest.test(rect.isIntersecting(circ3), true, "rect-circle intersect 3");
            UnitTest.test(rect.isIntersecting(circ2), true, "rect-circle intersect 2");
            UnitTest.test(rect.isIntersecting(circ1), true, "rect-circle intersect 1");
            UnitTest.test(rect.isIntersecting(circx2), false, "rect-circle not intersect 2");
            UnitTest.test(rect.isIntersecting(circx3), false, "rect-circle not intersect 1");
        }
    }


    //Draw Bally the Ball and Squarey the Square
    //WASD to pan and QE to zoom
    public static void GfxGraphicsTest() {
        //The x and y values are arbitrary, based on the camera size. Consider them "meters"
        SolidCircle ballybody = new SolidCircle(0, 0, 25, new RGB(0, 140, 255));
        SolidRectangle squareybody = new SolidRectangle(0, 0, 25, 25, new RGB(230, 20, 60));
        SolidCircle eyeball_edge_left = new SolidCircle(-10,0,8, new RGB(255, 255, 255));
        SolidCircle eyeball_pupl_left = new SolidCircle(-10,0,3, new RGB(0  , 0  , 0  ));
        SolidCircle eyeball_edge_rght = new SolidCircle( 10,0,8, new RGB(255, 255, 255));
        SolidCircle eyeball_pupl_rght = new SolidCircle( 10,0,3, new RGB(0  , 0  , 0  ));


        var bally_fig = new GfxFigure(-30, 0);
        bally_fig.shapes.add(ballybody);
        bally_fig.shapes.add(eyeball_edge_left);
        bally_fig.shapes.add(eyeball_pupl_left);
        bally_fig.shapes.add(eyeball_edge_rght);
        bally_fig.shapes.add(eyeball_pupl_rght);

        var squarey_fig = new GfxFigure(30, 0);
        squarey_fig.shapes.add(squareybody);
        squarey_fig.shapes.add(eyeball_edge_left);
        squarey_fig.shapes.add(eyeball_pupl_left);
        squarey_fig.shapes.add(eyeball_edge_rght);
        squarey_fig.shapes.add(eyeball_pupl_rght);

        GfxEngine engine = new GfxEngine(new Camera(0.1, 1920, 1080));
        GfxObjectHandle ballHandle = engine.add_gfx(bally_fig, "bally");
        GfxObjectHandle squareHandle = engine.add_gfx(squarey_fig, "squarey");
        engine.addKeyPressedEvent(new KeyRunnable() {
            public void run(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    engine.camera.pan(0, 25*engine.camera.xpp());
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    engine.camera.pan(0, -25*engine.camera.xpp());
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    engine.camera.pan(-25*engine.camera.ypp(), 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    engine.camera.pan(25*engine.camera.ypp(), 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    engine.camera.zoomCenter(1.05);
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    engine.camera.zoomCenter(1/1.05);
                }
            }
        });
        engine.decoration(Constants.isDecorated);
        engine.init();
        engine.changeRefreshRate(60);
    }
}
