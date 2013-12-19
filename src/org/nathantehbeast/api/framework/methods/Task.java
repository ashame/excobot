package org.nathantehbeast.api.framework.methods;

import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Contextable;

/**
 * Created by Nathan on 12/17/13.
 */
public abstract class Task extends Contextable implements Runnable {

    public Task(final Context ctx) {
        super(ctx);
    }

}
