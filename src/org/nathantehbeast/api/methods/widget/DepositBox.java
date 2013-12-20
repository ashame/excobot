package org.nathantehbeast.api.methods.widget;

import org.excobot.game.api.methods.cache.media.Widgets;
import org.excobot.game.api.util.Time;
import org.excobot.game.api.util.Timer;
import org.excobot.game.api.util.impl.Filter;
import org.excobot.game.api.wrappers.cache.media.Component;
import org.excobot.game.api.wrappers.media.Item;
import org.excobot.game.api.wrappers.media.animable.object.GameObject;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Nathan on 12/19/13.
 */
public class DepositBox extends Provider { //TODO: Rewrite deposit portions to comply with OS components.

    public DepositBox(Context ctx) {
        super(ctx);
    }

    public final int WIDGET_DEPOSIT_BOX = 11;
    public final int WIDGET_SLOTS_CONTAINER = 61;
    public final int WIDGET_BUTTON_CLOSE_DEPOSIT_BOX = 64;

    public boolean isOpen() {
        final Component depositBox = Widgets.getComponent(WIDGET_DEPOSIT_BOX, WIDGET_SLOTS_CONTAINER);
        return depositBox != null && depositBox.isVisible();
    }

    public boolean open() {
        final GameObject depositBox = getNearest();
        if (depositBox == null) {
            return false;
        }
        if (!depositBox.isOnGameScreen() && (!ctx.players.local().isMoving() || ctx.calculations.distance(ctx.movement.getDestination(), depositBox.getLocation()) > 4)) {
            ctx.movement.walkTo(depositBox);
            Time.sleep(200, 400);
        }
        if (depositBox.isOnGameScreen() && depositBox.interact("Deposit")) {
            final Timer t = new Timer(4000);
            while (t.isRunning() && !isOpen()) {
                Time.sleep(10);
            }
        }
        return isOpen();
    }

    public boolean close() {
        if (!isOpen()) {
            return false;
        }
        final Component closeButton = Widgets.getComponent(WIDGET_DEPOSIT_BOX, WIDGET_BUTTON_CLOSE_DEPOSIT_BOX);
        return closeButton != null && closeButton.interact("Close");
    }

    public GameObject getNearest() {
        return ctx.gameObjects.getNearest("Bank deposit box");
    }

    public Point getItemPoint(final int index) {
        final int column = (index % 8);
        final int row = (index / 8);
        final int p1 = 147 + (column * 40);
        final int p2 = 90 + (row * 42);
        return new Point(p1, p2);
    }

    public Item[] getItems() {
        return getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return true;
            }
        });
    }

    public Item[] getItems(final int... ids) {
        return getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                final int id = item.getId();
                for (int i : ids) {
                    if (id == i) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public Item[] getItems(final Filter<Item> filter) {
        if (!isOpen()) {
            return new Item[0];
        }
        final Item[] items = Widgets.getComponent(WIDGET_DEPOSIT_BOX, WIDGET_SLOTS_CONTAINER).getWidgetItems();
        final ArrayList<Item> arr = new ArrayList<>();
        for (final Item itm : items) {
            final Item item;
            if (itm.getComponent().getId() != -1 && filter.accept(item = itm)) {
                arr.add(item);
            }
        }
        return arr.toArray(new Item[arr.size()]);
    }

    public int[] getItemIds() {
        if (!isOpen()) {
            return new int[0];
        }
        return Widgets.getComponent(WIDGET_DEPOSIT_BOX, WIDGET_SLOTS_CONTAINER).getItems();
    }

    public Item getItem(final int id) {
        return getItem(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return item.getId() == id;
            }
        });
    }

    public Item getItem(final Filter<Item> filter) {
        for (final Item item : getItems()) {
            if (filter.accept(item)) {
                return item;
            }
        }
        return null;
    }

    public int getItemCount(final int... ids) {
        return getItemCount(false, new Filter<Item>() {
            public boolean accept(final Item item) {
                final int id = item.getId();
                for (final int i : ids) {
                    if (i == id) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public int getItemCount(final boolean countStack, final int... ids) {
        return getItemCount(countStack, new Filter<Item>() {
            public boolean accept(final Item item) {
                final int id = item.getId();
                for (final int i : ids) {
                    if (i == id) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public int getItemCount(final boolean countStack, final Filter<Item> filter) {
        int count = 0;
        for (final Item item : getItems()) {
            if (filter.accept(item)) {
                count += countStack ? item.getStackSize() : 1;
            }
        }
        return count;
    }

    public int getItemCount(final boolean countStack) {
        int count = 0;
        for (final Item item : getItems()) {
            count += countStack ? item.getStackSize() : 1;
        }
        return count;
    }

    public boolean slotContainsAction(final Component slot, final String action) {
        final String[] actions = slot.getActions();
        if (actions != null) {
            for (final String s : actions) {
                if (s != null && s.matches("^" + action + "(<.*>)?$")) {
                    return true;
                }
            }
        }
        return false;
    }

}
