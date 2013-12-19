package org.nathantehbeast.api.framework.methods;

import org.excobot.game.api.util.impl.Filter;
import org.excobot.game.api.wrappers.media.animable.GroundItem;
import org.excobot.game.api.wrappers.scene.Tile;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathan on 12/18/13.
 */
public class GroundItems extends Provider {

    public GroundItems(Context ctx) {
        super(ctx);
    }

    public GroundItem[] getLoaded() {
        return org.excobot.game.api.methods.media.animable.GroundItems.getLoaded();
    }

    public GroundItem[] getLoaded(final int... ids) {
        return getLoaded(new Filter<GroundItem>() {
            @Override
            public boolean accept(GroundItem groundItem) {
                final int ID = groundItem.getId();
                for (final int i : ids) {
                    if (ID == i) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public GroundItem[] getLoaded(final Filter<GroundItem> filter) {
        return getLoaded(104, filter);
    }

    public GroundItem[] getLoaded(final int range, final Filter<GroundItem> filter) {
        final Set<GroundItem> groundItems = new LinkedHashSet<>();
        for (final GroundItem item : getLoaded()) {
            if (filter.accept(item) && ctx.calculations.getDistance(item) < range) {
                groundItems.add(item);
            }
        }
        return groundItems.toArray(new GroundItem[groundItems.size()]);
    }

    public GroundItem[] getLoadedAt(final Tile t) {
        return getLoaded(new Filter<GroundItem>() {
            @Override
            public boolean accept(GroundItem groundItem) {
                return groundItem.getLocation().equals(t);
            }
        });
    }

    public GroundItem getNearest(final int... ids) {
        return (GroundItem) ctx.calculations.getNearest(getLoaded(new Filter<GroundItem>() {
            @Override
            public boolean accept(GroundItem groundItem) {
                final int ID = groundItem.getId();
                for (final int i : ids) {
                    if (ID == i) {
                        return true;
                    }
                }
                return false;
            }
        }));
    }

    public GroundItem getNearest(final Filter<GroundItem> filter) {
        return (GroundItem) ctx.calculations.getNearest(getLoaded(filter));
    }
}
