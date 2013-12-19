package org.nathantehbeast.api.framework.context;

import org.nathantehbeast.api.framework.Script;
import org.nathantehbeast.api.framework.methods.*;

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

    public Context(Script script) {
        this.script = script;

        this.calculations = new Calculations(this);
        this.inventory = new Inventory(this);
        this.movement = new Movement(this);
        this.skills = new Skills(this);
        this.players = new Players(this);
    }

    public Script getScript() {
        return this.script;
    }
}
