import GfxEngine.Camera.Point;
import Resources.Gfx;
import GfxEngine.Camera;
import GfxEngine.GfxEngine;
import GfxEngine.GfxFigure;
import GfxEngine.GfxObjectHandle;
import GfxEngine.RGB;
import GfxEngine.SolidCircle;
import GfxEngine.SolidRectangle;

public class Dev {
    public class UnitTest {
        public static void test(boolean got, boolean expected, String name) {
            int l = name.length();
            if (got == expected) {
                System.out.println("Test " + name + String.format("%"+(32-l)+"s", "")+ " passed");
            } else {
                System.out.println("Test " + name + String.format("%"+(32-l)+"s", "")+ " FAILED X");
            }
        }
    }
    public static void main(String[] args) {
        GfxTests();
    }

    public static void GfxTests() {
        //Code testing
        {
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
        {
            SolidCircle myObj0 = new SolidCircle(0, 0, 25, new RGB(0, 140, 255));
            SolidRectangle myObj1 = new SolidRectangle(0, 0, 30, 20, new RGB(230, 20, 60));
            SolidCircle eyeball_edge_left = new SolidCircle(-10,0,8, new RGB(255, 255, 255));
            SolidCircle eyeball_pupl_left = new SolidCircle(-10,0,3, new RGB(0  , 0  , 0  ));
            SolidCircle eyeball_edge_rght = new SolidCircle( 10,0,8, new RGB(255, 255, 255));
            SolidCircle eyeball_pupl_rght = new SolidCircle( 10,0,3, new RGB(0  , 0  , 0  ));

            var eyeball_fig = new GfxFigure();
            eyeball_fig.shapes.add(myObj0);
            eyeball_fig.shapes.add(eyeball_edge_left);
            eyeball_fig.shapes.add(eyeball_pupl_left);
            eyeball_fig.shapes.add(eyeball_edge_rght);
            eyeball_fig.shapes.add(eyeball_pupl_rght);

            Camera camera = new Camera(-100, -100, 100, 100, 1920, 1080);
            GfxEngine engine = new GfxEngine(camera);
            GfxFigure fig2 = new GfxFigure();
            fig2.shapes.add(myObj1);
            GfxObjectHandle ballHandle = engine.add(eyeball_fig, "eyes");
            engine.init();
        }
    }
}
