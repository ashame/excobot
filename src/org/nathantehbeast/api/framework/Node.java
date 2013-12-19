package org.nathantehbeast.api.framework;

import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

/**
 * Created by Nathan on 12/17/13.
 */
public abstract class Node extends Provider {

    public Node(Context arg0) {
        super(arg0);
    }

    public abstract boolean activate();

    public abstract void execute();
}
