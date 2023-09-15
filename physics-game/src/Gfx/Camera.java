package gfx;
import gfx.Snippets.xMath;

public class Camera {
    private View2D view = new View2D(null, null, null, null);
    private int width;
    private int height;

    public static class Point {
        public double x;
        public double y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    public static class Pixel {
        public int x;
        public int y;
        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * A class that holds the dimensions that a Camera views.
     * One value is allowed to be null, which will mean that that value with be solved for based on pixel size upon 
     * sending the object to Camera with setView().
     * 
     * If more fields are null or the View2D is incorrectly set, it may result in an error.
     */
    public static class View2D {
        public Double x1;
        public Double y1;
        public Double x2;
        public Double y2;
        public View2D(Double x1, Double y1, Double x2, Double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        public static View2D inferHeight(double x1, double x2, double y1) {
            View2D view = new View2D(x1, y1, x2, null);
            return view;
        }
        public static View2D inferWidth(double y1, double y2, double x1) {
            View2D view = new View2D(x1, y1, null, y2);
            return view;
        }
    }

    public Camera(double x1, double y1, double x2, double y2, int w, int h) {
        view.x1 = x1;
        view.y1 = y1;
        view.x2 = x2;
        view.y2 = y2;
        width = w;
        height = h;
    }

    /**
     * Centered at w,h
     */
    public Camera(double unitsPerPixel, int w, int h) {
        view.x1 = - w * unitsPerPixel / 2;
        view.y1 = - h * unitsPerPixel / 2;
        view.x2 = w * unitsPerPixel / 2;
        view.y2 = h * unitsPerPixel / 2;
        width = w;
        height = h;
    }

    public void zoomCenter(double coeff) {
        coeff = 1 / coeff;
        double x = (view.x1 + view.x2) / 2;
        double y = (view.y1 + view.y2) / 2;
        view.x1 = x - (x - view.x1) * coeff;
        view.y1 = y - (y - view.y1) * coeff;
        view.x2 = x - (x - view.x2) * coeff;
        view.y2 = y - (y - view.y2) * coeff;
    }
    
    public void pan(double dx, double dy) {
        view.x1 += dx;
        view.x2 += dx;
        view.y1 += dy;
        view.y2 += dy;
    }

    public void setView(View2D view) {
        int nullCount = 4 - (xMath.dimension(view.x1) + xMath.dimension(view.y1) + xMath.dimension(view.x2) + xMath.dimension(view.y2));
        if (nullCount > 1) {
            System.out.println(String.valueOf(nullCount));
            throw new IllegalArgumentException("View2D cannot infer more than one value");
        }
        if (nullCount == 1) {
            if (view.y2 == null) {
                double y2 = view.y1 + (view.x2 - view.x1) / width * height;    
            }
            else if (view.x2 == null) {
                double x2 = view.x1 + (view.y2 - view.y1) / height * width;
            }
            else if (view.y1 == null) {
                double y1 = view.y2 - (view.x2 - view.x1) / width * height;
            }
            else if (view.x1 == null) {
                double x1 = view.x2 - (view.y2 - view.y1) / height * width;
            }
        }
    }

    public View2D getView() {
        return view;
    }

    public void setScreenDimensions(int w, int h) {
        width = w;
        height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public Pixel map(Point p) {
        //All y operations are reversed because the first pixel is at the top.
        int x = mapx(p.x);
        int y = mapy(p.y);
        return new Pixel(x, y);
    }

    public Point reverseMap(Pixel p) {
        double x = (double) p.x / width * (view.x2 - view.x1) + view.x1;
        double y = (double) p.y / height * (view.y1 - view.y2) + view.y2;
        return new Point(x, y);
    }

    public int mapx(double x) {
        return (int) ((x - view.x1) / (view.x2 - view.x1) * width);
    }

    public int mapy(double y) {
        return (int) ((y - view.y2) / (view.y1 - view.y2) * height);
    }

    public double reverseMapx(int x) {
        return (double) x / width * (view.x2 - view.x1) + view.x1;
    }

    public double reverseMapy(int y) {
        return (double) y / height * (view.y1 - view.y2) + view.y2;
    }

    public int mapxscalar(double scalar) {
        return (int) Math.abs(Math.ceil(scalar / (view.x2 - view.x1) * width));
    }

    public int mapyscalar(double scalar) {
        return (int) Math.abs(Math.ceil(scalar / (view.y1 - view.y2) * height));
    }

    public double reverseMapxscalar(int scalar) {
        return (double) Math.abs(scalar / width * (view.x2 - view.x1));
    }

    public double reverseMapyscalar(int scalar) {
        return (double) Math.abs(scalar / height * (view.y1 - view.y2));
    }

    /**
     * x coordinate per pixel value
     */
    public double xpp() {
        return (view.x2 - view.x1) / width;
    }

    /**
     * y coordinate per pixel value, negative because upwards for points is by default downwards for pixels
     */
    public double ypp() {
        return (view.y1 - view.y2) / height;
    }

}