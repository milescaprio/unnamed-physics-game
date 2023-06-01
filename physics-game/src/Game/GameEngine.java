package Game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gfx.GfxEngine;
import gfx.GfxEngine.KeyEventHandle;
import gfx.Snippets.KeyRunnable;

/**
 * A class which holds a GfxEngine Graphics Engine and a Game Tick timer, for this project.
 * The game ticks 100 times per second. Framerate should be handled asynchronously in the GfxEngine
 */
public class GameEngine {
    public GfxEngine gfxEngine;
    private Timer ticker;
    private int tick;
    private ArrayList<TickSchedule> events;
    public GameEngine(GfxEngine gfx) {
        ticker = new Timer();
        gfxEngine = gfx;
        events = new ArrayList<TickSchedule>();
    }
    public void initAll() {
        ticker.schedule(new TimerTask() {
            public void run() {
                tick++;
                for (TickSchedule event : events) {
                    if (event != null)
                    if (tick % event.interval == 0) {
                        event.task.run();
                    }
                }
            }
        }, 0, 10);
        gfxEngine.init();
    }
    public TickScheduleHandle addSchedule(int tickInterval, Runnable task) {
        events.add(new TickSchedule(tickInterval, task));
        return new TickScheduleHandle(events.size() - 1);
    }
    public void removeSchedule(TickScheduleHandle handle) {
        events.set(handle.id(), null);
    }

    public KeyEventHandle addKeyTickBind(int keyCode, Runnable task) {
        KeyRunnable runnable = new KeyRunnable () {
            public TickScheduleHandle handle = null;
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == keyCode) {
                    if(handle != null) removeSchedule(handle);
                    handle = addSchedule(1, task);
                }
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == keyCode && handle != null) {
                    removeSchedule(handle);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
        };
        return gfxEngine.addKeyEvent(runnable);
    }

    public void removeKeyTickBind(KeyEventHandle handle) {
        gfxEngine.removeKeyEvent(handle);
    }

    private class TickSchedule {
        public int interval;
        public Runnable task;
        public TickSchedule(int interval, Runnable task) {
            this.interval = interval;
            this.task = task;
        }
    }
    public class TickScheduleHandle {
        private int id;
        public TickScheduleHandle(int id) {
            this.id = id;
        }
        public int id() {
            return id;
        }
    }
}