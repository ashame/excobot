package org.nathantehbeast.api.methods.interactive;

import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;
import org.nathantehbeast.api.wrappers.interactive.Player;

/**
 * Created by Nathan on 12/18/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class Players extends Provider {

    public Players(Context ctx) {
        super(ctx);
    }

    public Player local() {
        return new Player(org.excobot.game.api.methods.media.animable.actor.Players.getLocal(), ctx);
    }
}
