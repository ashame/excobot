package org.nathantehbeast.scripts.bdk.jobs;

import org.excobot.game.api.wrappers.scene.Tile;
import org.nathantehbeast.api.framework.Job;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.wrappers.Area;

/**
 * Created by Nathan on 12/30/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class Fight extends Job {

    private final Area DRAGON_AREA = new Area(new Tile(1, 1, 1), new Tile(1, 1, 1));

    public Fight(Context arg0) {
        super(arg0);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().getHealthPercent() > 30 && DRAGON_AREA.contains(ctx.players.local()) && ctx.players.local().getAnimation() == -1;
    }

    @Override
    public void execute() {

    }
}
