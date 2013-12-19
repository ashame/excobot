package org.nathantehbeast.api.framework.methods;

import org.excobot.game.api.util.impl.Filter;
import org.excobot.game.api.wrappers.media.animable.actor.NPC;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nathan on 12/18/13.
 */
public class NPCs extends Provider {

    public NPCs(Context ctx) {
        super(ctx);
    }

    public NPC[] getLoaded() {
        return getLoaded(new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return true;
            }
        });
    }

    public NPC[] getLoaded(final int... ids) {
        return getLoaded(new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                for (final int id : ids) {
                    if (npc.getId() == id) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public NPC[] getLoaded(final Filter<NPC> filter) {
        final Set<NPC> npcs = new HashSet<>();
        for (NPC npc : org.excobot.game.api.methods.media.animable.actor.NPCs.getLoaded()) {
            if (npc != null && !npcs.contains(npc)) {
                npcs.add(npc);
            }
        }
        return npcs.toArray(new NPC[npcs.size()]);
    }

    public NPC getNearest(final int... ids) {
        return (NPC) ctx.calculations.getNearest(getLoaded(ids));
    }

    public NPC getNearest(final String... names) {
        return (NPC) ctx.calculations.getNearest(getLoaded(new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                final String name = npc.getName();
                if (name == null) {
                    return false;
                }
                for (final String n : names) {
                    if (n.toLowerCase().contains(name.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }

        }));
    }
}
