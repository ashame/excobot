package org.nathantehbeast.api.framework.context;

/**
 * Created by Nathan on 12/17/13.
 */
public class Provider {

    public Context ctx;

    public Provider(Context ctx) {
        this.ctx = ctx;
    }

    public Context getContext() {
        return ctx;
    }
}


