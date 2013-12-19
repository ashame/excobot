package org.nathantehbeast.api.framework.methods;

import org.nathantehbeast.api.framework.context.Context;

/**
 * Created by Nathan on 12/17/13.
 */
public abstract class LoopTask extends Task {

    public LoopTask(final Context ctx) {
        super(ctx);
    }

    public abstract int loop();

    @Override
    public void run() {
        while (ctx.getScript().isRunning()) {
            try {
                final int wait = loop();
                if (wait > 0) {
                    Thread.sleep(wait);
                } else {
                    ctx.getScript().setRunning(false);
                }
            } catch (final Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
