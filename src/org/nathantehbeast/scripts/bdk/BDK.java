package org.nathantehbeast.scripts.bdk;

import org.nathantehbeast.api.framework.Script;

import java.awt.*;

/**
 * Created by Nathan on 12/30/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class BDK extends Script {
    @Override
    public boolean setup() {
        return false;
    }

    @Override
    public void onRepaint(Graphics2D g) {
        g.drawString("Runtime: ", 10, 15);
    }
}
