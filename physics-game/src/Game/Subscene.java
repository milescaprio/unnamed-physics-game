package Game;

import java.util.ArrayList;

import gfx.GfxObject;
import gfx.Camera.View2D;
import gfx.Snippets.Awaiter;
import gfx.Snippets.KeyRunnable;
import Game.GameEngine.TickSchedule;

public class Subscene {
    public ArrayList<GfxObject> subsceneObjects;
    public ArrayList<KeyRunnable> subsceneKeyEvents;
    public ArrayList<TickSchedule> subsceneTickSchedules;
    public View2D subsceneCamera;
    public Awaiter subsceneAwation;

    public Subscene(Awaiter awaiter) {
        subsceneObjects = new ArrayList<GfxObject>();
        subsceneKeyEvents = new ArrayList<KeyRunnable>();
        subsceneTickSchedules = new ArrayList<TickSchedule>();
        subsceneAwation = awaiter;
    }

    public Subscene(ArrayList<GfxObject> subsceneObjects, ArrayList<KeyRunnable> subsceneKeyObjects, ArrayList<TickSchedule> subsceneTickSchedules, View2D subsceneCamera, Awaiter awation) {
        this.subsceneObjects = subsceneObjects;
        this.subsceneKeyEvents = subsceneKeyObjects;
        this.subsceneTickSchedules = subsceneTickSchedules;
        this.subsceneCamera = subsceneCamera;
        this.subsceneAwation = awation;
    }

    public Subscene(GfxObject[] subsceneObjects, KeyRunnable[] subsceneKeyObjects, TickSchedule[] subsceneTickSchedules, View2D subsceneCamera, Awaiter awation) {
        this.subsceneObjects = new ArrayList<GfxObject>();
        for (GfxObject obj : subsceneObjects) {
            this.subsceneObjects.add(obj);
        }
        this.subsceneKeyEvents = new ArrayList<KeyRunnable>();
        for (KeyRunnable obj : subsceneKeyObjects) {
            this.subsceneKeyEvents.add(obj);
        }
        this.subsceneTickSchedules = new ArrayList<TickSchedule>();
        for (TickSchedule obj : subsceneTickSchedules) {
            this.subsceneTickSchedules.add(obj);
        }
        this.subsceneCamera = subsceneCamera;
        this.subsceneAwation = awation;
    }
}
