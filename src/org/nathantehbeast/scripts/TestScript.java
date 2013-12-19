package org.nathantehbeast.scripts;

import org.excobot.bot.script.Category;
import org.excobot.bot.script.Manifest;
import org.excobot.game.api.methods.cache.Game;
import org.nathantehbeast.api.framework.Script;
import org.nathantehbeast.api.framework.methods.LoopTask;
import org.nathantehbeast.api.framework.methods.Task;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by Nathan on 12/18/13.
 */

@Manifest(
        authors = "Nathan",
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
        ctx.getScript().submit(new LoopTask(ctx) {
            @Override
            public int loop() {
                if (Game.getGameState() == Game.States.LOGGED_IN) {
                    log("Skill Levels: " + Arrays.toString(ctx.skills.getLevels()));
                    log("Skill Experiences: " + Arrays.toString(ctx.skills.getExperiences()));
                    log("Total Level: " + ctx.skills.getTotalLevel());
                    log("Total Experience: " + ctx.skills.getTotalExperience());
                    String[] s = new String[ctx.inventory.getItems().length];
                    for (int i = 0; i < ctx.inventory.getItems().length; i++) {
                        s[i] = ctx.inventory.getItems()[i].getName();
                    }
                    log("Items: " + Arrays.toString(s));
                }
                log("LoopTask sleeping for 10000ms");
                return 10000;
            }
        });
        ctx.getScript().submit(new Task(ctx) {
            @Override
            public void run() {
                log("Task completed.");
            }
        });
        return true;
    }

    @Override
    public void loop() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void onRepaint(Graphics2D g) {

    }
}
