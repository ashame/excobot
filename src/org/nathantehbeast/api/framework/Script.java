package org.nathantehbeast.api.framework;

import org.excobot.Application;
import org.excobot.bot.event.listeners.PaintListener;
import org.excobot.bot.script.GameScript;
import org.excobot.game.api.util.Time;
import org.nathantehbeast.api.framework.context.Context;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nathan on 12/17/13.
 */
public abstract class Script extends GameScript implements PaintListener {

    private final List<Job> container = Collections.synchronizedList(new ArrayList<Job>());
    public Job currentJob;
    protected int delay = 600;
    public final long startTime;
    public Context ctx;
    private boolean running;

    public Script() {
        this.ctx = new Context(this);
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public Context getContext() {
        return ctx;
    }

    public synchronized final void provide(final Job... jobs) {
        if (jobs != null) {
            for (Job job : jobs) {
                if (!container.contains(job)) {
                    container.add(job);
                    log("Providing: " + job);
                }
            }
        }
    }

    @Override
    public boolean start() {
        return setup();
    }

    public abstract boolean setup();

    @Override
    public int execute() {
        try {
            if (container != null) {
                synchronized (container) {
                    for (Job job : container) {
                        if (job.activate()) {
                            job.execute();
                            currentJob = job;
                        }
                    }
                }
            }
            loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delay;
    }

    protected void loop() {}

    @Override
    public void onFinish() {
        this.running = false;
        exit();
    }

    protected void exit() {}

    @Override
    public void repaint(Graphics g) {
        onRepaint((Graphics2D) g);
    }

    protected void onRepaint(Graphics2D g) {}

    public void log(Object x) {
        System.out.println("[" + ctx.calculations.getFormattedTime() + "] " + x);
        Application.log("[" + ctx.calculations.getFormattedTime() + "] " + x);
    }

    public void submit(final Runnable task) {
        final Thread t = new Thread(getThreadGroup(), task);
        t.start();
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean b) {
        this.running = b;
    }

}
