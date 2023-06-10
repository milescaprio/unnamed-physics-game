import Resources.Constants;
import gfx.Snippets.KeyRunnable;
import Snippets.UnitTest;

import java.awt.event.KeyEvent;

import Game.GameEngine;
import gfx.Camera;
import gfx.GfxEngine;
import gfx.GfxFigure;
import gfx.RGB;
import gfx.SolidCircle;
import gfx.SolidRectangle;
import gfx.SolidTriangle;
import gfx.Camera.Point;
import gfx.GfxEngine.GfxObjectHandle;

//
// Hey guys this is the head of testing the program
// Skim the code in this file and run it
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
        //To Do: Write more unit tests for intersections
    }


    //Draw Bally the Ball and Squarey the Square
    //WASD to pan and QE to zoom
    /**
     * 
     */
    public static void GfxGraphicsTest() {
        //The x and y values are arbitrary, based on the camera size. Consider them "meters"
        SolidCircle ballybody = new SolidCircle(0, 0, 25, new RGB(0, 140, 255));
        SolidRectangle squareybody = new SolidRectangle(0, 0, 25, 25, new RGB(230, 20, 60));
        SolidCircle eyeball_edge_left = new SolidCircle(-10,0,8, new RGB(255, 255, 255));
        SolidCircle eyeball_pupl_left = new SolidCircle(-10,0,3, new RGB(0  , 0  , 0  ));
        SolidCircle eyeball_edge_rght = new SolidCircle( 10,0,8, new RGB(255, 255, 255));
        SolidCircle eyeball_pupl_rght = new SolidCircle( 10,0,3, new RGB(0  , 0  , 0  ));
        SolidTriangle hat = new SolidTriangle(0, 0, -20, 25, 20, 25, 0, 40, new RGB(230, 180, 20));

        GfxFigure bally_fig = new GfxFigure(-30, 30);
        bally_fig.addShape(ballybody);
        bally_fig.addShape(eyeball_edge_left);
        bally_fig.addShape(eyeball_pupl_left);
        bally_fig.addShape(eyeball_edge_rght);
        bally_fig.addShape(eyeball_pupl_rght);

        GfxFigure squarey_fig = new GfxFigure(30, 30);
        squarey_fig.addShape(squareybody);
        squarey_fig.addShape(eyeball_edge_left);
        squarey_fig.addShape(eyeball_pupl_left);
        squarey_fig.addShape(eyeball_edge_rght);
        squarey_fig.addShape(eyeball_pupl_rght);
        squarey_fig.addShape(hat);

        
        GameEngine engine = new GameEngine(new GfxEngine(new Camera(0.1, 1920, 1080), Constants.title));
        // for (int i = 0; i < 360; i++) {
        //     GfxFigure littlecircle = new GfxFigure(0, 0);
        //     littlecircle.addShape(new SolidCircle(Math.cos(Math.toRadians(i)) * 100, Math.sin(Math.toRadians(i)) * i, 1, new RGB(255, 255, 255)));
        //     engine.gfxEngine.add_gfx(littlecircle, "little circle " + i);
        // }

        GfxFigure bigcircle = new GfxFigure(0, 0);
        GfxFigure bigbigcircle = new GfxFigure(0, 0);
        GfxFigure bigbigbigcircle = new GfxFigure(0, 0);
        GfxFigure smallcircle = new GfxFigure(0, 0);
        GfxFigure smallsmallcircle = new GfxFigure(0, 0);
        GfxFigure smallsmallsmallcircle = new GfxFigure(0, 0);
        bigcircle.addShape(new SolidCircle(0, 0, 100, new RGB(255, 255, 255)));
        bigbigcircle.addShape(new SolidCircle(0, 0, 1000, new RGB(0, 0, 0)));
        bigbigbigcircle.addShape(new SolidCircle(0, 0, 10000, new RGB(255, 255, 255)));
        smallcircle.addShape(new SolidCircle(0, 0, 10, new RGB(0, 0, 0)));
        smallsmallcircle.addShape(new SolidCircle(0, 0, 1, new RGB(255, 255, 255)));
        smallsmallsmallcircle.addShape(new SolidCircle(0, 0, 0.1, new RGB(0, 0, 0)));

        GfxObjectHandle bigBigBigCirlceHandle = engine.gfxEngine.add_gfx(bigbigbigcircle, null);
        GfxObjectHandle bigBigCirlceHandle = engine.gfxEngine.add_gfx(bigbigcircle, null);
        GfxObjectHandle bigCirlceHandle = engine.gfxEngine.add_gfx(bigcircle, null);
        GfxObjectHandle smallCirlceHandle = engine.gfxEngine.add_gfx(smallcircle, null);
        GfxObjectHandle smallSmallCirlceHandle = engine.gfxEngine.add_gfx(smallsmallcircle, null);
        GfxObjectHandle smallSmallSmallCirlceHandle = engine.gfxEngine.add_gfx(smallsmallsmallcircle, null);
        GfxObjectHandle ballHandle = engine.gfxEngine.add_gfx(bally_fig, "bally");
        GfxObjectHandle squareHandle = engine.gfxEngine.add_gfx(squarey_fig, "squarey");
        var xAxisFigure = new GfxFigure(0,0);
        var yAxisFigure = new GfxFigure(0,0);
        xAxisFigure.addShape(new SolidRectangle(0, 0, 1000000, 0.1, new RGB(0,0,0)));
        yAxisFigure.addShape(new SolidRectangle(0, 0, 0.1, 100000, new RGB(0,0,0)));
        // GfxObjectHandle xAxisHandle = engine.gfxEngine.add_gfx(xAxisFigure, "x axis");
        // GfxObjectHandle yAxisHandle = engine.gfxEngine.add_gfx(yAxisFigure, "y axis");
        engine.addKeyEvent(engine.mergeRunnables(
            engine.keyTickBindingRunnable(KeyEvent.VK_W, new Runnable() {
                public void run() {
                    engine.gfxEngine.camera.pan(0, -5*engine.gfxEngine.camera.ypp()); //Up is negative pixels
            }}),
            engine.keyTickBindingRunnable(KeyEvent.VK_S, new Runnable() {
                public void run() {
                    engine.gfxEngine.camera.pan(0, 5*engine.gfxEngine.camera.ypp());
            }}),
            engine.keyTickBindingRunnable(KeyEvent.VK_D, new Runnable() {
                public void run() {
                    engine.gfxEngine.camera.pan(5*engine.gfxEngine.camera.xpp(), 0);
            }}),
            engine.keyTickBindingRunnable(KeyEvent.VK_A, new Runnable() {
                public void run() {
                    engine.gfxEngine.camera.pan(-5*engine.gfxEngine.camera.xpp(), 0);
            }}),
            engine.keyTickBindingRunnable(KeyEvent.VK_E, new Runnable() {
                public void run() {
                    engine.gfxEngine.camera.zoomCenter(1.01);
            }}),
            engine.keyTickBindingRunnable(KeyEvent.VK_Q, new Runnable() {
                public void run() {
                    engine.gfxEngine.camera.zoomCenter(1/1.01);
            }})));
        engine.gfxEngine.decoration(Constants.isDecorated);
        engine.initAll();
        engine.gfxEngine.fullscreen();
        engine.gfxEngine.adaptRefreshRate();
    }
}



































// engine.addKeyTickBind(KeyEvent.VK_W, new Runnable() {
//     public void run() {
//         engine.gfxEngine.camera.pan(0, -5*engine.gfxEngine.camera.ypp()); //Up is negative pixels
// }});
// engine.addKeyTickBind(KeyEvent.VK_S, new Runnable() {
//     public void run() {
//         engine.gfxEngine.camera.pan(0, 5*engine.gfxEngine.camera.ypp());
// }});
// engine.addKeyTickBind(KeyEvent.VK_D, new Runnable() {
//     public void run() {
//         engine.gfxEngine.camera.pan(5*engine.gfxEngine.camera.xpp(), 0);
// }});
// engine.addKeyTickBind(KeyEvent.VK_A, new Runnable() {
//     public void run() {
//         engine.gfxEngine.camera.pan(-5*engine.gfxEngine.camera.xpp(), 0);
// }});
// engine.addKeyTickBind(KeyEvent.VK_E, new Runnable() {
//     public void run() {
//         engine.gfxEngine.camera.zoomCenter(1.01);
// }});
// engine.addKeyTickBind(KeyEvent.VK_Q, new Runnable() {
//     public void run() {
//         engine.gfxEngine.camera.zoomCenter(1/1.01);
// }});