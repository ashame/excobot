package org.nathantehbeast.api.framework.context;

/**
 * Created by Nathan on 12/17/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class Contextable extends Provider {

    protected final Context context;

    public Contextable(final Context ctx) {
        super(ctx);
        this.context = ctx;
    }
}
