package Game;

import java.util.ArrayList;
import java.util.Arrays;

import Game.GameEngine.TickSchedule;
import Game.GameEngine.TickScheduleHandle;
import gfx.Camera.View2D;
import gfx.Debug;
import gfx.GfxObject;
import gfx.GfxEngine.GfxObjectHandle;
import gfx.GfxEngine.KeyEventHandle;
import gfx.Snippets.Awaiter;
import gfx.Snippets.KeyRunnable;


/**
 * A single game scene, which holds a linear progression of multiple subscenes.
 * Not a dynamic scene. Progressive. Keybinds can be assigned, however, and then handle the scene (manipulating the added GfxObjects or Camera)
 * Can be extended for a speicific scene type. However, usable as is.
 */
public class Scene {
    //Hold handles to all the open scene elements in order, where the first part of the arraylist holds the more permanent elements.
    //To remove all the subscene elements, remove all the elements down until the amount of the corresponding default scene elements
    private ArrayList<KeyEventHandle> openKeyEvents;
    private ArrayList<GfxObjectHandle> openGfxObjects;
    private ArrayList<TickScheduleHandle> openTickSchedules;
    
    private GameEngine head;
    private int subsceneid = 0;
    private int subscenes = 0;
    private View2D defaultCam;
    private ArrayList<GfxObject> sceneObjects;
    private ArrayList<KeyRunnable> sceneKeyEvents;
    private ArrayList<TickSchedule> sceneTickSchedules;
    private ArrayList<Awaiter> subsceneAwations;
    private ArrayList<ArrayList<TickSchedule>> subsceneTickSchedules;
    private ArrayList<ArrayList<KeyRunnable>> subsceneKeyEvents;
    private ArrayList<ArrayList<GfxObject>> subsceneObjects;
    private ArrayList<View2D> subsceneCameras;
    
    /**
     * Creates a new large Scene, with a default scene and multiple subscenes.
     * @param game The game engine to add the scene to.
     * @param backgroundDefaultScene The default scene, which is the scene that is always running in the background, behind the other scenes.
     * @param subscenes The subscenes, which are the scenes that are run in order, after the default scene.
     * Each subscene is not required to provide a camera; if not, then the camera will be the same as the last scene's.
     */
    public Scene(GameEngine game, BgScene backgroundDefaultScene, ArrayList<Subscene> subscenes) {
        //copy game
        this.head = game;
        
        //initialize empty AL's
        {
            this.openKeyEvents = new ArrayList<KeyEventHandle>();
            this.openGfxObjects = new ArrayList<GfxObjectHandle>();
            this.openTickSchedules = new ArrayList<TickScheduleHandle>();
            this.sceneObjects = new ArrayList<GfxObject>();
            this.sceneKeyEvents = new ArrayList<KeyRunnable>();
            this.sceneTickSchedules = new ArrayList<TickSchedule>();
            this.subsceneAwations = new ArrayList<Awaiter>();
            this.subsceneTickSchedules = new ArrayList<ArrayList<TickSchedule>>();
            this.subsceneKeyEvents = new ArrayList<ArrayList<KeyRunnable>>();
            this.subsceneObjects = new ArrayList<ArrayList<GfxObject>>();
            this.subsceneCameras = new ArrayList<View2D>();
        }
        
        //transfer default scene
        sceneObjects = backgroundDefaultScene.subsceneObjects;
        sceneKeyEvents = backgroundDefaultScene.subsceneKeyEvents;
        sceneTickSchedules = backgroundDefaultScene.subsceneTickSchedules;
        defaultCam = backgroundDefaultScene.subsceneCamera;

        //copy subscenes
        this.subscenes = subscenes.size();
        for (Subscene ss : subscenes) {
            this.subsceneAwations.add(ss.subsceneAwation);
            this.subsceneTickSchedules.add(ss.subsceneTickSchedules);
            this.subsceneKeyEvents.add(ss.subsceneKeyEvents);
            this.subsceneObjects.add(ss.subsceneObjects);
            this.subsceneCameras.add(ss.subsceneCamera);
        }
    }

    public Scene(GameEngine game, BgScene backgroundSubscene, Subscene[] subscenes) {
        this(game, backgroundSubscene, new ArrayList<Subscene>(Arrays.asList(subscenes)));
    }

    public void init() {
        if (Debug.notify == true) {
            System.out.println("Scene: init()");
        }
        //Add to the beginning of the open handles the default scene elements, and initialize them on the game engine
        for (int i = 0;i<sceneObjects.size();i++) {
            openGfxObjects.add(head.gfxEngine.add_gfx(sceneObjects.get(i)));
        }
        for (int i = 0;i<sceneKeyEvents.size();i++) {
            openKeyEvents.add(head.gfxEngine.addKeyEvent(sceneKeyEvents.get(i)));
        }
        for (int i = 0;i<sceneTickSchedules.size();i++) {
            openTickSchedules.add(head.addSchedule(sceneTickSchedules.get(i)));
        }
        head.gfxEngine.camera.setView(defaultCam);

        class IterativeNextScene implements Runnable {
            public void run() {
                if (Debug.notify == true) {
                    System.out.println("Scene: IterativeNextScene: add stuff to key event queue");
                }
                head.gfxEngine.addToPostKeyEventQueue(new Runnable() {
                    public void run() {
                        if (Debug.notify == true) {
                            System.out.println("Scene: IterativeNextScene: run()");
                        }       
                        if (!next()) {
                            if (Debug.notify == true) {
                                System.out.println("Scene: IterativeNextScene: next() called");
                            }       
                            free();
                            if (Debug.notify == true) {
                                System.out.println("Scene: IterativeNextScene: free() called");
                            }       
                        } else {
                            if (Debug.notify == true) {
                                System.out.println("Scene: IterativeNextScene: next() called");
                            }       
                            try {
                                //Not recursive, delegative recursive. The current run() exits, and the awaiter is passed
                                //as a virtual new thread, as it creates its own attachment.
                                //AwaitKeyPress adds to the event queue in the event of a key press.
                                //This is okay of a function to add to the event queue, because it runs a function
                                //Which adds adding to the event queue in the keylistener.
                                //Jesus Christ!
                                subsceneAwations.get(subsceneid).await(new IterativeNextScene());
                            } catch (Exception e) {
                                if (Debug.explosive) {
                                    System.out.println("Exception in await in iterativerun");
                                    if (Debug.oneError) {
                                        e.printStackTrace();
                                    }
                                    System.exit(0);
                                } else {
                                    throw e;
                                }
                            }
                        }
                    }
                });
            }
        }
        
        if (Debug.notify == true) {
            System.out.println(subsceneAwations);
        }

        initSubscene();
        subsceneAwations.get(0).await(new IterativeNextScene());
    }

    /**
     * Returns false if the scene is finished, and true if the scene is not finished.
     */
    private boolean next() {
        //Remove all the open handles for the subscene and destroy them on the game engine.
        //Then add the new subscene's handles to the open handles, and initialize them on the game engine
        freeSubscene();
        subsceneid++;
        if (subsceneid >= subscenes) {
            return false;
        }
        if (Debug.Sceneing) {
            System.out.println("Scene: next(): " + (subsceneid >= subscenes ? "True" : "False"));
        }
        initSubscene();
        return true;
    }
    
    private void initSubscene() {
        for (int i = 0;i<subsceneObjects.get(subsceneid).size();i++) {
            openGfxObjects.add(head.gfxEngine.add_gfx(subsceneObjects.get(subsceneid).get(i)));
        }
        for (int i = 0;i<subsceneKeyEvents.get(subsceneid).size();i++) {
            openKeyEvents.add(head.gfxEngine.addKeyEvent(subsceneKeyEvents.get(subsceneid).get(i)));
        }
        for (int i = 0;i<subsceneTickSchedules.get(subsceneid).size();i++) {
            openTickSchedules.add(head.addSchedule(subsceneTickSchedules.get(subsceneid).get(i)));
        }
        if (subsceneCameras.get(subsceneid) != null) {
            head.gfxEngine.camera.setView(subsceneCameras.get(subsceneid));
        }
    }

    private void freeSubscene() {
        for (int i = openGfxObjects.size();i-->sceneObjects.size();) { //The last destroyed gfx is sceneObjects.size(), the last non-sceneObject
            head.gfxEngine.remove_gfx(openGfxObjects.get(i));
            openGfxObjects.remove(i);
        }
        if (Debug.deadlock) {
            System.out.println("Scene: freeSubscene(): keyevents locked: " + (head.gfxEngine.viewKeyEventLock() ? "True" : "False"));
        }
        for (int i = openKeyEvents.size();i-->sceneKeyEvents.size();) {
            head.gfxEngine.removeKeyEvent(openKeyEvents.get(i));
            openKeyEvents.remove(i);
        }
        for (int i = openTickSchedules.size();i-->sceneTickSchedules.size();) {
            head.removeSchedule(openTickSchedules.get(i));
            openTickSchedules.remove(i);
        }
    }

    private void free() {
        for (int i = openKeyEvents.size();i-->0;) {
            head.gfxEngine.removeKeyEvent(openKeyEvents.get(i));
            openKeyEvents.remove(i);
        }
        for (int i = openGfxObjects.size();i-->0;) {
            head.gfxEngine.remove_gfx(openGfxObjects.get(i));
            openGfxObjects.remove(i);
        }
        for (int i = openTickSchedules.size();i-->0;) {
            head.removeSchedule(openTickSchedules.get(i));
            openTickSchedules.remove(i);
        }
    }
}