package org.nathantehbeast.api.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Nathan
 * Date: 10/8/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Utilities {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static boolean contains(final int[] array, final int id) {
        for (final int i : array) {
            if (i == id) {
                return true;
            }
        }
        return false;
    }

    public static Image getImage(final String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            Logger.log(e.toString());
            return null;
        }
    }

    public static boolean loadFont(int type, String url) {
        try {
            URL fontUrl = new URL(url);
            Font font = Font.createFont(type, fontUrl.openStream());
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            Logger.log("Successfully registered Font: " + font.getFontName());
            return true;
        } catch (Exception e) {
            Logger.log("Error loading font: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void openFile(final String path) {
        try {
            File file = new File(path);
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
                desktop.open(file);
            }
        } catch (IOException e) {
            Logger.log(e.toString());
        }
    }

    public static void openFile(final File file) {
        try {
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
                desktop.open(file);
            }
        } catch (IOException e) {
            Logger.log(e.toString());
        }
    }

    public static String getFormattedTime() {
        return DATE_FORMAT.format(Calendar.getInstance().getTime());
    }
}
