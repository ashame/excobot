package org.nathantehbeast.api.framework;

import org.excobot.bot.event.listeners.PaintListener;
import org.excobot.bot.script.GameScript;
import org.excobot.game.api.util.Time;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.tools.Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nathan on 12/17/13.
 */
public abstract class Script extends GameScript implements PaintListener {

    private final List<Node> container = Collections.synchronizedList(new ArrayList<Node>());
    public Node currentNode;
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

    public synchronized final void provide(final Node... nodes) {
        if (nodes != null) {
            for (Node node : nodes) {
                if (!container.contains(node)) {
                    container.add(node);
                    log("Providing: " + node);
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
                    for (Node node : container) {
                        if (node.activate()) {
                            node.execute();
                            currentNode = node;
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
        log("Stopping script.");
        log("Total runtime: " + Time.format(System.currentTimeMillis() - startTime));
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
        System.out.println("[" + Utilities.getFormattedTime() + "] " + x);
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
