package org.nathantehbeast.api.framework.methods;

import org.excobot.game.client.Client;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.util.ArrayList;

/**
 * Created by Nathan on 12/18/13.
 */
public class Skills extends Provider {

    public Skills(Context ctx) {
        super(ctx);
    }

    public final int[] XP_TABLE = {0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107,
            2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
            16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983,
            75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742,
            302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
            1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
            3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
            11805606, 13034431, 14391160, 15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
            31777943, 35085654, 38737661, 42769801, 47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
            85539082, 94442737, 104273167};

    public int[] getLevels() {
        final int[] levels = new int[23];
        for (int i = 0; i < 23; i++) {
            levels[i] = org.excobot.game.api.methods.tab.Skills.values()[i].getRealLevel();
        }
        return levels;
    }

    public int[] getExperiences() {
        final int[] experiences = new int[23];
        for (int i = 0; i < 23; i++) {
            experiences[i] = org.excobot.game.api.methods.tab.Skills.values()[i].getExperience();
        }
        return experiences;
    }

    public int getLevelAt(final int xp) {
        for (int i = XP_TABLE.length - 1; i > 0; i--) {
            if (xp > XP_TABLE[i]) {
                return i;
            }
        }
        return 1;
    }

    public int getLevel(final int i) {
        return getLevels()[i];
    }

    public int getRealLevel(int i) {
        return getLevelAt(getExperiences()[i]);
    }

    public int getExperience(final int i) {
        return getExperiences()[i];
    }

    public int getExperienceRequired(final int l) {
        if (l > 99) {
            return -1;
        }
        return XP_TABLE[l];
    }

    public int getExperienceToLevel(final int i, final int l) {
        if (l > 99) {
            return -1;
        }
        return getExperienceRequired(l) - getExperiences()[i];
    }

    public int getTotalLevel() {
        int l = 0;
        for (int i : getLevels()) {
            l = l + i;
        }
        return l;
    }

    public int getTotalExperience() {
        int x = 0;
        for (int i : getExperiences()) {
            x = x + i;
        }
        return x;
    }

}
