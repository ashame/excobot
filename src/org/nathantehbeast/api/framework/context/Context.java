package org.nathantehbeast.api.framework.context;

import org.nathantehbeast.api.framework.Script;
import org.nathantehbeast.api.framework.methods.Calculations;
import org.nathantehbeast.api.framework.methods.Inventory;
import org.nathantehbeast.api.framework.methods.Skills;
import org.nathantehbeast.api.framework.methods.Walking;

/**
 * Created by Nathan on 12/17/13.
 */

public class Context {

    public Script script;

    public Calculations calculations;
    public Inventory inventory;
    public Walking walking;
    public Skills skills;

    public Context(Script script) {
        this.script = script;

        this.calculations = new Calculations(this);
        this.inventory = new Inventory(this);
        this.walking = new Walking(this);
        this.skills = new Skills(this);
    }

    public Script getScript() {
        return this.script;
    }
}
