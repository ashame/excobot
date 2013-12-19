package org.nathantehbeast.api.framework;

import org.excobot.bot.event.listeners.PaintListener;
import org.excobot.bot.script.GameScript;
import org.excobot.game.api.util.Time;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.methods.LoopTask;
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
    public int delay = 600;
    public final long startTime;
    public Context ctx;

    public Script() {
        this.ctx = new Context();
        this.startTime = System.currentTimeMillis();
    }

    public int getContainerSize() {
        return container.size();
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
        log(ctx);
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

    public abstract void loop();

    @Override
    public void onFinish() {
        log("Stopping Script.");
        log("Total runtime: "+ Time.format(System.currentTimeMillis() - startTime));
        ctx.shutdownExecutor();
        exit();
    }

    public abstract void exit();

    @Override
    public void repaint(Graphics g) {
        onRepaint((Graphics2D) g);
    }

    public abstract void onRepaint(Graphics2D g);

    public void log(Object x) {
        System.out.println("[" + Utilities.getFormattedTime() + "] " + x);
    }


}
