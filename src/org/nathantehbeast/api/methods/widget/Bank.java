package org.nathantehbeast.api.methods.widget;

import org.excobot.game.api.methods.cache.media.Menu;
import org.excobot.game.api.methods.cache.media.Widgets;
import org.excobot.game.api.util.Time;
import org.excobot.game.api.util.Timer;
import org.excobot.game.api.util.impl.Filter;
import org.excobot.game.api.wrappers.Entity;
import org.excobot.game.api.wrappers.Identifiable;
import org.excobot.game.api.wrappers.Locatable;
import org.excobot.game.api.wrappers.cache.media.Component;
import org.excobot.game.api.wrappers.cache.media.Widget;
import org.excobot.game.api.wrappers.media.Item;
import org.excobot.game.api.wrappers.media.animable.actor.NPC;
import org.excobot.game.api.wrappers.media.animable.object.GameObject;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nathan on 12/19/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class Bank extends Provider { //TODO: deposit/withdrawal methods that comply with 07

    public Bank(Context ctx) {
        super(ctx);
    }

    public final String[] BANK_NAMES_BANKERS = {"Banker"};
    public final String[] BANK_NAMES_BOOTHS = {"Bank booth", "Bank chest", "Counter"};
    public final String[] BANK_NAMES_CHESTS = {};
    public final String[] BANK_NAMES_COUNTERS = {};

    //TODO: Grab widget/component/setting values
    public final int WIDGET_BANK = -1;
    public final int COMPONENT_SLOTS_CONTAINER = -1;
    public final int WIDGET_BUTTON_CLOSE_BANK = -1;
    public final int WIDGET_BUTTON_DEPOSIT_INVENTORY = -1;
    public final int WIDGET_BUTTON_DEPOSIT_EQUIPMENT = -1;
    public final int WIDGET_BUTTON_SEARCH = -1;
    public final int WIDGET_BUTTON_WITHDRAW_NOTED = -1;


    public final int WIDGET_BANKPIN = -1;

    public final int SETTING_WITHDRAWAL_MODE = -1;


    private final Filter<Identifiable> ALL_FILTER = new Filter<Identifiable>() {
        @Override
        public boolean accept(Identifiable identifiable) {
            return true;
        }
    };

    public enum Amount {
        ONE(1), FIVE(5), TEN(10), ALL_BUT_ONE(-1), ALL(0);

        private final int value;

        private Amount(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Widget getWidget() {
        return Widgets.get(WIDGET_BANK);
    }

    public boolean isOpen() {
        final Component bank = getWidget().getChild(COMPONENT_SLOTS_CONTAINER);
        return bank != null && bank.isVisible();
    }

    public boolean open() {
        if (isOpen()) {
            return true;
        }
        final Entity bank = getNearest();
        if (bank == null) {
            return false;
        }
        if (!bank.isOnGameScreen() && (!ctx.players.local().isMoving() || ctx.calculations.distance(ctx.movement.getDestination(), bank.getLocation()) > 4)) {
            ctx.movement.walkTo(bank);
            Time.sleep(200, 400);
        }
        if (bank.isOnGameScreen()) {
            boolean interacted = false;
            if (isBanker((Identifiable) bank)) {
                interacted = bank.interact("Bank");
            } else if (isBankBooth((Identifiable) bank)) {
                interacted = bank.interact("Bank", "Bank booth");
            } else if (isBankBooth((Identifiable) bank)) {
                bank.hover();
                interacted = Menu.contains("Open") ? bank.interact("Open") : bank.interact("Use");
            } else if (isBankCounter((Identifiable) bank)) {
                interacted = bank.interact("Bank", "Counter");
            }
            final Timer t = new Timer(4000);
            while (t.isRunning() && interacted && !isOpen()) {
                Time.sleep(10);
            }
        }
        return isOpen();
    }

    public boolean close() {
        if (!isOpen()) {
            return true;
        }
        final Component closeButton = Widgets.getComponent(WIDGET_BANK, WIDGET_BUTTON_CLOSE_BANK);
        return closeButton != null && closeButton.interact("Close");
    }

    public boolean depositInventory() {
        if (!isOpen()) {
            return false;
        }
        if (ctx.inventory.isEmpty()) {
            return true;
        }
        final Component button = Widgets.getComponent(WIDGET_BANK, WIDGET_BUTTON_DEPOSIT_INVENTORY);
        final int invCount = ctx.inventory.getCount();
        if (button != null && button.click(true)) {
            final Timer t = new Timer(2000);
            while (t.isRunning() && ctx.inventory.getCount() == invCount) {
                Time.sleep(5);
            }
        }
        return invCount != ctx.inventory.getCount();
    }

    public boolean depositEquipment() {
        if (!isOpen()) {
            return false;
        }
        final Component button = Widgets.getComponent(WIDGET_BANK, WIDGET_BUTTON_DEPOSIT_EQUIPMENT);
        return button != null && button.click(true);
    }

    public Item[] getItems() {
        return getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return item != null;
            }
        });
    }

    public Item[] getItems(final Filter<Item> itemFilter) {
        if (!isOpen()) {
            return new Item[0];
        }
        final Item[] allItems = Widgets.getComponent(WIDGET_BANK, COMPONENT_SLOTS_CONTAINER).getWidgetItems();
        final ArrayList<Item> items = new ArrayList<Item>(allItems.length);
        for (final Item item : allItems) {
            if (item != null && itemFilter.accept(item)) {
                items.add(item);
            }
        }
        return items.toArray(new Item[items.size()]);
    }

    public int getItemCount(final boolean countStacks) {
        return getItemCount(countStacks, new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return item != null;
            }
        });
    }

    public int getItemCount(final boolean countStacks, final Filter<Item> itemFilter) {
        int count = 0;
        for (final Item item : getItems()) {
            if (item != null && itemFilter.accept(item)) {
                count += countStacks ? item.getStackSize() : 1;
            }
        }
        return count;
    }

    public Entity getNearest() {
        final Locatable[] banks = getLoadedBanks();
        Locatable nearest = null;
        for (final Locatable bank : banks) {
            if (ALL_FILTER.accept((Identifiable) bank) && ((Entity) bank) != null) {
                if ((nearest == null || ctx.calculations.distanceTo(bank) < ctx.calculations.distanceTo(nearest))) {
                    nearest = bank;
                }
            }
        }
        return (Entity) nearest;
    }

    private Locatable[] getLoadedBanks() {
        final ArrayList<Locatable> banks = new ArrayList<>();
        final NPC[] loadedNPCs = ctx.npcs.getLoaded(BANK_NAMES_BANKERS);
        for (final NPC npc : loadedNPCs) {
            if (npc != null && ALL_FILTER.accept(npc)) {
                banks.add(npc);
            }
        }
        final GameObject[] loadedObjects = ctx.gameObjects.getLoaded();
        for (final GameObject object : loadedObjects) {
            if (object != null && ALL_FILTER.accept(object)) {
                banks.add(object);
            }
        }
        return banks.toArray(new Locatable[banks.size()]);
    }

    private boolean isBanker(final Identifiable identifiable) {
        return identifiable instanceof NPC && Arrays.binarySearch(BANK_NAMES_BANKERS, ((NPC) identifiable).getName()) >= 0;
    }

    private boolean isBankBooth(final Identifiable identifiable) {
        return identifiable instanceof GameObject && Arrays.binarySearch(BANK_NAMES_BOOTHS, ((GameObject) identifiable).getName()) >= 0;
    }

    private boolean isBankCounter(final Identifiable identifiable) {
        return identifiable instanceof GameObject && Arrays.binarySearch(BANK_NAMES_COUNTERS, ((GameObject) identifiable).getName()) >= 0;
    }

    private boolean isBankChest(final Identifiable identifiable) {
        return identifiable instanceof GameObject && Arrays.binarySearch(BANK_NAMES_CHESTS, ((GameObject) identifiable).getName()) >= 0;
    }
}
