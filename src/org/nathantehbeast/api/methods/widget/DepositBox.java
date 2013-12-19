package org.nathantehbeast.api.methods.widget;

import org.excobot.game.api.methods.cache.media.Widgets;
import org.excobot.game.api.methods.input.Keyboard;
import org.excobot.game.api.methods.media.Bank;
import org.excobot.game.api.util.Time;
import org.excobot.game.api.util.Timer;
import org.excobot.game.api.util.impl.Filter;
import org.excobot.game.api.wrappers.cache.media.Component;
import org.excobot.game.api.wrappers.media.Item;
import org.excobot.game.api.wrappers.media.animable.object.GameObject;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.util.ArrayList;

/**
 * Created by Nathan on 12/19/13.
 */
public class DepositBox extends Provider {

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

    public boolean deposit(final int id, final Bank.Amount amount) {
        return deposit(id, amount.getIndex());
    }

    public boolean deposit(final int id, final int amount) {
        final Item item = getItem(id);
        if (!isOpen() || item == null || amount < 0) {
            return false;
        }
        String action = "Deposit-" + amount;
        if (getItemCount(true, id) <= amount || amount == 0) {
            action = "Deposit-All";
        }
        final int invCount = getItemCount(true);
        if (slotContainsAction(item.getComponent(), action)) {
            if (!item.getComponent().interact(action)) {
                return false;
            }
        } else if (item.getComponent().interact("Deposit-X")) { //TODO: Get component ID of input
            Time.sleep(200, 800);
            Keyboard.sendKeys(String.valueOf(amount), true);
        }
        final Timer t = new Timer(2000);
        while (t.isRunning() && getItemCount(true) == invCount) {
            Time.sleep(5);
        }
        return getItemCount(true) != invCount;
    }

    public boolean depositInventory() {
        if (!isOpen()) {
            return false;
        }
        if (getItems().length == 0) {
            return true;
        }
        final int invCount = getItems().length;
        final ArrayList<Integer> ids = new ArrayList<>();
        for (Item item : getItems()) {
            if (item != null && !ids.contains(item.getId())) {
                ids.add(item.getId());
            }
        }
        for (int id : ids) {
            deposit(id, Bank.Amount.ALL);
        }
        return invCount != getItems().length;
    }

    public Item[] getItems() {
        return getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return true;
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
