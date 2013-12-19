package org.nathantehbeast.api.framework.methods;

import org.nathantehbeast.api.framework.context.Context;
import org.nathantehbeast.api.framework.context.Provider;

import java.text.DecimalFormat;

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
}
