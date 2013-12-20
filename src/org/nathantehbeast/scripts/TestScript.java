package org.nathantehbeast.scripts;

import org.excobot.bot.script.Category;
import org.excobot.bot.script.Manifest;
import org.excobot.game.api.util.Time;
import org.nathantehbeast.api.framework.Script;

import java.awt.*;

/**
 * Created by Nathan on 12/18/13.
 */

@Manifest(
        authors = {"Nathan", "Not Kenneh"},
        name = "Test Script",
        description = "A test script.",
        version = 1.0,
        catagory = Category.OTHER
)

public class TestScript extends Script {

    @Override
    public boolean setup() {
        log("Script started!");
        delay = 600;
        return true;
    }

    @Override
    public void onRepaint(Graphics2D g) {
        g.drawString("Current Job: " + currentJob, 5, 15);
        g.drawString("Runtime: " + Time.format(System.currentTimeMillis() - startTime), 5, 30);
    }
}
