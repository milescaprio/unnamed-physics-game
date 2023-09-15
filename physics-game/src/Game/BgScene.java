package Game;

import java.util.ArrayList;

import Game.GameEngine.TickSchedule;
import gfx.GfxObject;
import gfx.Camera.View2D;
import gfx.Snippets.Awaiter;
import gfx.Snippets.KeyRunnable;

/**
 * Functionally equivalent of Subscene
 * Represents that the scene is a background scene, and should be drawn first.
 */
public class BgScene extends Subscene {
    public BgScene() {
        super(new Awaiter() {
            public void await(Runnable when) {
                //do nothing
            }});
    }

    public BgScene(ArrayList<GfxObject> subsceneObjects, ArrayList<KeyRunnable> subsceneKeyObjects, ArrayList<TickSchedule> subsceneTickSchedules, View2D defaultCamera) {
        super(subsceneObjects, subsceneKeyObjects, subsceneTickSchedules, defaultCamera, new Awaiter() {
            public void await(Runnable when) {
                //do nothing
            }});
    }

    public BgScene(GfxObject[] subsceneObjects, KeyRunnable[] subsceneKeyObjects, TickSchedule[] subsceneTickSchedules, View2D defaultCamera) {
        super(subsceneObjects, subsceneKeyObjects, subsceneTickSchedules, defaultCamera, new Awaiter() {
            public void await(Runnable when) {
                //do nothing
            }});
    }
}
