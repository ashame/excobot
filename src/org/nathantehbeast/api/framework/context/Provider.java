package org.nathantehbeast.api.framework.context;

import java.util.logging.Logger;

/**
 * Created by Nathan on 12/17/13.
 */
public class Provider {

    public Context ctx;
    public Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public Provider(Context ctx) {
        this.ctx = ctx;
    }
}


