package org.nathantehbeast.api.framework.methods;

import org.excobot.game.api.wrappers.media.Item;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

/**
 * Created by Nathan on 12/17/13.
 */
public class Inventory extends Provider {

    public Inventory(Context ctx) {
        super(ctx);
    }

    public boolean isEmpty() {
        return org.excobot.game.api.methods.tab.Inventory.isEmpty();
    }

    public boolean isFull() {
        return org.excobot.game.api.methods.tab.Inventory.isFull();
    }

    public boolean contains(int id) {
        for (Item item : org.excobot.game.api.methods.tab.Inventory.getItems()) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Item[] getItems() {
        return org.excobot.game.api.methods.tab.Inventory.getItems();
    }

    public Item getItemAt(int i) {
        return getItems()[i];
    }


}
