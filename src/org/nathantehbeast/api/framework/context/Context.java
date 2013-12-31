package org.nathantehbeast.api.framework.context;

import org.nathantehbeast.api.framework.Script;
import org.nathantehbeast.api.methods.Calculations;
import org.nathantehbeast.api.methods.Movement;
import org.nathantehbeast.api.methods.interactive.*;
import org.nathantehbeast.api.methods.node.GameObjects;
import org.nathantehbeast.api.methods.node.GroundItems;
import org.nathantehbeast.api.methods.tab.Skills;
import org.nathantehbeast.api.methods.widget.DepositBox;
import org.nathantehbeast.api.methods.tab.Inventory;

/**
 * Created by Nathan on 12/17/13.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class Context {

    public Script script;

    public Calculations calculations;
    public Inventory inventory;
    public Movement movement;
    public Skills skills;
    public Players players;
    public NPCs npcs;
    public GroundItems groundItems;
    public GameObjects gameObjects;
    public DepositBox depositBox;

    public Context(Script script) {
        this.script = script;

        this.calculations = new Calculations(this);
        this.inventory = new Inventory(this);
        this.movement = new Movement(this);
        this.skills = new Skills(this);
        this.players = new Players(this);
        this.npcs = new NPCs(this);
        this.groundItems = new GroundItems(this);
        this.gameObjects = new GameObjects(this);
        this.depositBox = new DepositBox(this);
    }

    public Script getScript() {
        return this.script;
    }
}
