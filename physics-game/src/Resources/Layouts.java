package Resources;
import java.util.ArrayList;

import gfx.GfxObject;

//To be written, by anyone: level layouts (includes timeline and objects)

public class Layouts {
    public static interface Layout {
        public ArrayList<GfxObject> getObjects();
        public ArrayList<Progression> getTimeline();
        //To be implemented, for each level, by anyone
    }

    public static interface Progression {
        //To be written and implemented, by anyone
    }
}