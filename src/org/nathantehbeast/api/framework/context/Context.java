package org.nathantehbeast.api.framework.context;

import org.nathantehbeast.api.framework.Script;
import org.nathantehbeast.api.methods.*;
import org.nathantehbeast.api.methods.interactive.*;
import org.nathantehbeast.api.methods.node.*;
import org.nathantehbeast.api.methods.tab.*;
import org.nathantehbeast.api.methods.widget.*;

/**
 * Created by Nathan on 12/17/13.
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
