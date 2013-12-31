package org.nathantehbeast.api.framework;

import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

/**
 * Created by Nathan on 12/17/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public abstract class Task extends Provider implements Runnable {

    public Task(final Context ctx) {
        super(ctx);
    }

}
