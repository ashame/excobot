package org.nathantehbeast.api.methods;

import org.excobot.game.api.wrappers.Entity;
import org.excobot.game.api.wrappers.Locatable;
import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nathan
 * Date: 10/12/13
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class Calculations extends Provider {

    public Calculations(Context ctx) {
        super(ctx);
    }

    public final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public String formatNumber(final int num) {
        final DecimalFormat df = new DecimalFormat("0.0");
        final StringBuilder sb = new StringBuilder();
        if (num >= 1000000) {
            sb.append(df.format(num / 1000000)).append("m");
        } else if (num >= 1000) {
            sb.append(df.format(num / 1000)).append("k");
        } else {
            sb.append(num);
        }
        return sb.toString();
    }

    public int perHour(final int gained, final long start) {
        return (int) ((gained) * 3600000D / (System.currentTimeMillis() - start));
    }

    public Entity getNearest(final Entity[] entities) {
        final List<Entity> list = Arrays.asList(entities);
        final Comparator<Entity> comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return (int) (getDistance(o1) - getDistance(o2));
            }
        };
        Collections.sort(list, comparator);
        return list.size() > 0 ? list.get(0) : null;
    }

    public double getDistance(final Entity entity) {
        return entity.getLocation().distance(ctx.players.local());
    }

    public double distance(final Entity o1, final Entity o2) {
        return getDistance(o1) - getDistance(o2);
    }

    public double getDistance(final Locatable l) {
        return l.getLocation().distance(ctx.players.local());
    }

    public double distance(final Locatable l1, final Locatable l2) {
        return getDistance(l1) - getDistance(l2);
    }

    public double distance(final Point p1, final Point p2) {
        return Point.distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public String getFormattedTime() {
        return DATE_FORMAT.format(Calendar.getInstance().getTime());
    }

    public Image getImage(final String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            getContext().getScript().log("Error loading image: " + e.getMessage());
           e.printStackTrace();
           return null;
        }
    }

    public boolean loadFont(int type, String url) {
        try{
            URL fontUrl = new URL(url);
            Font font = Font.createFont(type, fontUrl.openStream());
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            getContext().getScript().log("Successfully registered font: " + font.getFontName());
            return true;
        } catch (Exception e) {
            getContext().getScript().log("Error loading font: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
