package org.nathantehbeast.api.framework.context;

import org.nathantehbeast.api.framework.methods.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nathan on 12/17/13.
 */

public class Context {

    private final ExecutorService executor;
    private boolean running;

    public Calculations calculations;
    public Inventory inventory;
    public Walking walking;
    public Skills skills;

    public Context() {
        this.running = true;
        this.executor = Executors.newCachedThreadPool();

        this.calculations = new Calculations(this);
        this.inventory = new Inventory(this);
        this.walking = new Walking(this);
        this.skills = new Skills(this);
    }

    public void shutdownExecutor() {
        this.running = false;
        executor.shutdown();
    }

    public ExecutorService getExecutor() {
        return this.executor;
    }

    public boolean isRunning() {
        return this.running;
    }
}
