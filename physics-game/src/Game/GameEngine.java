package Game;

import java.util.List;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import gfx.GfxEngine;
import gfx.GfxEngine.KeyEventHandle;
import gfx.Snippets.KeyRunnable;

/**
 * A class which holds a GfxEngine Graphics Engine and a Game Tick timer, for this project.
 * The game ticks a certain amount times per second, by default 10. Framerate should be handled asynchronously in the GfxEngine
 * 
 * Do not establish schedules in schedule functions, as this will cause a ConcurrentModificationException.
 * TODO: Add locking system for schedules.
 */
public class GameEngine {
    public GfxEngine gfxEngine;
    private Timer ticker;
    private int tick;
    private int tickLengthMs = 10;
    private List<TickSchedule> events;
    public GameEngine(GfxEngine gfx) {
        ticker = new Timer();
        gfxEngine = gfx;
        events = Collections.synchronizedList(new ArrayList<TickSchedule>());
    }
    public GameEngine(GfxEngine gfx, int tickLengthMs) {
        this(gfx);
        this.tickLengthMs = tickLengthMs;
    }
    public void initAll() {
        ticker.schedule(new TimerTask() {
            public void run() {
                synchronized(events) {
                    tick++;
                    for (TickSchedule event : events) {
                        if (event != null)
                        if (tick % event.interval == 0) {
                            event.task.run();
                        }
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

    public TickScheduleHandle addSchedule(TickSchedule schedule) {
        events.add(schedule);
        return new TickScheduleHandle(events.size() - 1);
    }

    public void removeSchedule(TickScheduleHandle handle) {
        events.set(handle.id(), null);
    }

    public KeyEventHandle addKeyEvent(KeyRunnable runnable) {
        return gfxEngine.addKeyEvent(runnable);
    }

    public void removeKeyEvent(KeyEventHandle handle) {
        gfxEngine.removeKeyEvent(handle);
    }

    public KeyEventHandle addKeyTickBind(int keyCode, Runnable task) {
        KeyRunnable runnable = keyTickBindingRunnable(keyCode, task);
        return gfxEngine.addKeyEvent(runnable);
    }

    public KeyRunnable keyTickBindingRunnable(int keyCode, Runnable task) {
        KeyRunnable ret = new KeyRunnable () {
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
        return ret;
    }

    public KeyRunnable mergeRunnables(KeyRunnable... runnables) {
        KeyRunnable ret = new KeyRunnable() {
            public void keyPressed(KeyEvent e) {
                for (KeyRunnable runnable : runnables) {
                    runnable.keyPressed(e);
                }
            }
            public void keyReleased(KeyEvent e) {
                for (KeyRunnable runnable : runnables) {
                    runnable.keyReleased(e);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
        };
        return ret;
    }

    public void removeKeyTickBind(KeyEventHandle handle) {
        gfxEngine.removeKeyEvent(handle);
    }

    public class TickSchedule {
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