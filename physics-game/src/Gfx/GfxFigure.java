package gfx;

import java.awt.Graphics;
import java.util.ArrayList;

public class GfxFigure implements GfxObject {
    private ArrayList<GfxShape> shapes;
    public double x;
    public double y;
    public GfxFigure(double x, double y) {
        this.shapes = new ArrayList<GfxShape>();
        this.x = x;
        this.y = y;
    }
    public ShapeHandle addShape(GfxShape shape) {
        shapes.add(shape);
        return new ShapeHandle(shapes.size() - 1);
    }
    public void removeShape(ShapeHandle handle) {
        shapes.set(handle.id(), null);
    }
    public boolean isIntersecting(GfxFigure other) {
        for (GfxShape shape : this.shapes) {
            for (GfxShape otherShape : other.shapes) {
                if (shape.isIntersecting(otherShape)) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void draw(Camera camera, Graphics g) {
        for (GfxShape shape : shapes) {
            shape.draw(camera, g, x, y);
        }
    }

    public static class ShapeHandle {
        private int id;
        public ShapeHandle(int id) {
            this.id = id;
        }
        public int id() {
            return id;
        }
    }
}






 









// public class Figure {
//     private ArrayList<GfxShape> shapes;
//     public boolean isColliding(Figure other) {
//         for (GfxShape shape : this.shapes) {
//             for (GfxShape otherShape : other.shapes) {
//                 if (shape.isColliding(otherShape)) {
//                     return true;
//                 }
//             }
//         }
//         return false;
//     }
//     public double collisionDegree(Figure other) {
//         //Trivial implementation for general shape collisions
//         double degree = 0;
//         int count = 0;
//         for (GfxShape shape : this.shapes) {
//             for (GfxShape otherShape : other.shapes) {
//                 degree += shape.collisionDegree(otherShape);
//                 count++;
//             }
//         } 
//         if (count == 0) return 0;
//         return degree / count;
//     }
// }
