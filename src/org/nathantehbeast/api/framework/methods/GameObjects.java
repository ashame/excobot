package org.nathantehbeast.api.framework.methods;

import org.excobot.game.api.util.impl.Filter;
import org.excobot.game.api.wrappers.media.animable.object.GameObject;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Nathan on 12/18/13.
 */
public class GameObjects extends Provider {

    public GameObjects(Context ctx) {
        super(ctx);
    }

    public GameObject[] getLoaded() {
        return org.excobot.game.api.methods.media.animable.GameObjects.getLoaded();
    }

    public GameObject[] getLoaded(final int... ids) {
        return getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
                final int id = gameObject.getId();
                for (final int i : ids) {
                    if (id == i) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public GameObject[] getLoaded(final Filter<GameObject> filter) {
        final Set<GameObject> objects = new LinkedHashSet<>();
        for (final GameObject g : getLoaded()) {
            if (g != null && filter.accept(g)) {
                objects.add(g);
            }
        }
        return objects.toArray(new GameObject[objects.size()]);
    }

    public GameObject getNearest(final int... ids) {
        return (GameObject) ctx.calculations.getNearest(getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
                final int id = gameObject.getId();
                for (final int i : ids) {
                    if (id == i) {
                        return true;
                    }
                }
                return false;
            }
        }));
    }

    public GameObject getNearest(final Filter<GameObject> filter) {
        return (GameObject) ctx.calculations.getNearest(getLoaded(filter));
    }

}
