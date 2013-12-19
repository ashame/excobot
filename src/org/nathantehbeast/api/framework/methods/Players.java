package org.nathantehbeast.api.framework.methods;

import org.excobot.game.api.wrappers.media.animable.actor.Player;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

/**
 * Created by Nathan on 12/18/13.
 */
public class Players extends Provider {

    public Players(Context ctx) {
        super(ctx);
    }

    public Player local() {
        return org.excobot.game.api.methods.media.animable.actor.Players.getLocal();
    }
}
