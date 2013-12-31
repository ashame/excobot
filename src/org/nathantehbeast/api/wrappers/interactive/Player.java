package org.nathantehbeast.api.wrappers.interactive;

import org.nathantehbeast.api.framework.context.Context;

/**
 * Created by Nathan on 12/30/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class Player extends org.excobot.game.api.wrappers.media.animable.actor.Player { //TODO: Fill this with random stuff that I'll never use

    private final Context ctx;

    public Player(org.excobot.game.client.Player a, Context ctx) {
        super(a);
        this.ctx = ctx;
    }

    public int getHealthPercent() {
        final int currentHealth = ctx.skills.getLevel(ctx.skills.CONSTITUTION);
        final int maxHealth = ctx.skills.getRealLevel(ctx.skills.CONSTITUTION);
        return currentHealth / maxHealth * 100;
    }
}
