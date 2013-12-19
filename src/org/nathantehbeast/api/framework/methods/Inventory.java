package org.nathantehbeast.api.framework.methods;

import org.excobot.game.api.util.impl.Filter;
import org.excobot.game.api.wrappers.media.Item;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.util.LinkedList;
import java.util.List;

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
        return getCount() == 28;
    }

    public boolean contains(final int... ids) {
        return getCount(false, ids) > 0;
    }

    public boolean contains(int id) {
        for (Item item : org.excobot.game.api.methods.tab.Inventory.getItems()) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(final int... ids) {
        for (final int id : ids) {
            if (getItem(id) == null) {
                return false;
            }
        }
        return true;
    }

    public int getCount() {
        return getItems().length;
    }

    public int getCount(final boolean includeStacks, final int... ids) {
        return getCount(includeStacks, new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return false;
            }
        });
    }

    public int getCount(final boolean includeStacks, final Filter<Item> filter) {
        int i = 0;
        for (Item item : getItems(filter)) {
            i += includeStacks ? item.getStackSize() : 1;
        }
        return i;
    }

    public Item[] getItems() {
        return org.excobot.game.api.methods.tab.Inventory.getItems();
    }

    public Item[] getItems(final Filter<Item> filter) {
        final List<Item> items = new LinkedList<>();
        for (final Item item : getItems()) {
            if (filter.accept(item)) {
                items.add(item);
            }
        }
        return items.toArray(new Item[items.size()]);
    }

    public Item getItemAt(final int i) {
        return getItems()[i];
    }

    public Item getItem(final int id) {
        return org.excobot.game.api.methods.tab.Inventory.getItem(id);
    }

}
