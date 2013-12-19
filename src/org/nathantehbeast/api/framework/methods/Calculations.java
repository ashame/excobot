package org.nathantehbeast.api.framework.methods;

import org.excobot.game.api.wrappers.Entity;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nathan
 * Date: 10/12/13
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class Calculations extends Provider {

    public Calculations(Context ctx) {
        super(ctx);
    }

    public String formatNumber(final int num) {
        final DecimalFormat df = new DecimalFormat("0.0");
        final StringBuilder sb = new StringBuilder();
        if (num >= 1000000) {
            sb.append(df.format(num / 1000000)).append("m");
        } else if (num >= 1000) {
            sb.append(df.format(num / 1000)).append("k");
        } else {
            sb.append(num);
        }
        return sb.toString();
    }

    public int perHour(final int gained, final long start) {
        return (int) ((gained) * 3600000D / (System.currentTimeMillis() - start));
    }

    public Entity getNearest(final Entity[] entities) {
        final List<Entity> list = Arrays.asList(entities);
        final Comparator<Entity> comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return getDistance(o1) - getDistance(o2);
            }
        };
        Collections.sort(list, comparator);
        return list.size() > 0 ? list.get(0) : null;
    }

    public int getDistance(final Entity entity) {
        return (int) entity.getLocation().distance(ctx.players.local());
    }
}
