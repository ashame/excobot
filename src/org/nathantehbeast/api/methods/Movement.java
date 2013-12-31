package org.nathantehbeast.api.methods;

import org.excobot.game.api.wrappers.Locatable;
import org.excobot.game.api.wrappers.scene.Tile;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

/**
 * Created by Nathan on 12/17/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class Movement extends Provider {

    public Movement(Context ctx) {
        super(ctx);
    }

    public void setRun(boolean on) {
        org.excobot.game.api.methods.scene.Movement.setRun(on);
    }

    public boolean walkTo(Locatable l) {
        return org.excobot.game.api.methods.scene.Movement.walkGameScreen(l);
    }

    public Tile getDestination() {
        return org.excobot.game.api.methods.scene.Movement.getDestination();
    }


}
