package org.nathantehbeast.api.framework.context;

/**
 * Created by Nathan on 12/17/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class Provider {

    protected final Context ctx;

    public Provider(Context ctx) {
        this.ctx = ctx;
    }

    public Context getContext() {
        return ctx;
    }
}


