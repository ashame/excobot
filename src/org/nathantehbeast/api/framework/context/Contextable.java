package org.nathantehbeast.api.framework.context;

/**
 * Created by Nathan on 12/17/13.
 */
public class Contextable extends Provider {

    protected final Context context;

    public Contextable(final Context ctx) {
        super(ctx);
        this.context = ctx;
    }
}
