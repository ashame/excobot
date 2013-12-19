package org.nathantehbeast.api.tools;

import org.excobot.bot.script.Manifest;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Nathan
 * Date: 5/28/13
 * Time: 3:04 AM
 * To change this template use File | Settings | File Templates.
 */

public class Logger {

    private static JTextArea textArea;
    private static JFrame frame;
    private static JLabel timeLabel;
    private static final Font defaultFont = new Font("Calibri", Font.PLAIN, 12);
    private static final Color defaultBackground = new Color(43, 43, 43);
    private static final Color defaultForeground = new Color(168, 182, 197);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss z");
    private static final Image img1 = null;
    private static final Image img2 = null;
    private static int parentWidth;
    private static int parentHeight;
    private static int modifiedHeight;

    /**
     * Creates a new Logger with the default font, background, and foreground. This should be called only once per script, in the onStart method.
     * For example: new Logger();
     */
    public Logger() {
        new Logger(defaultFont, defaultBackground, defaultForeground);
    }

    /**
     * Creates a new Logger with your specified font, default background color, and foreground color.
     *
     * @param font The font to use within the log pane.
     */
    public Logger(final Font font) {
        new Logger(font, defaultBackground, defaultForeground);
    }

    /**
     * Creates a new Logger with the default font, and your specified color on either the background or the foreground.
     *
     * @param color      The color of either the foreground or background.
     * @param background Whether to set the color as the background or foreground.
     */
    public Logger(final Color color, final boolean background) {
        if (background) {
            new Logger(defaultFont, color, defaultForeground);
        } else {
            new Logger(defaultFont, defaultBackground, color);
        }
    }

    /**
     * Creates a new Logger with your specified font, color on either the background or the foreground, and the default color for the other.
     *
     * @param font       The font to use within the log pane.
     * @param color      The color for either the background or foreground
     * @param background Whether to set the color as the background or foreground
     */
    public Logger(final Font font, final Color color, final boolean background) {
        if (background) {
            new Logger(font, color, defaultForeground);
        } else {
            new Logger(font, defaultBackground, color);
        }
    }

    /**
     * Creates a new Logger with the default font, your specified background color, foreground color.
     *
     * @param background The color to use for the background.
     * @param foreground The color to use for the foreground.
     */
    public Logger(final Color background, final Color foreground) {
        new Logger(defaultFont, background, foreground);
    }

    /**
     * Creates a new logger with your specified font, background color, foreground color, and height
     *
     * @param font       The font to use within the log pane.
     * @param background The color to use for the background.
     * @param foreground The color to use for the foreground.
     */
    public Logger(final Font font, final Color background, final Color foreground) {
        new Logger(font, background, foreground, 150);
    }

    /**
     * Creates a new logger with your specified font, background color, foreground color, and height.
     *
     * @param font       The font to use within the log pane.
     * @param background The color to use for the background.
     * @param foreground The color to use for the foreground.
     * @param height     The height of the log pane.
     */
    public Logger(final Font font, final Color background, final Color foreground, final int height) {
        new Logger(font, background, foreground, height, null);
    }

    /**
     * Creates a new logger with your specified font, background color, foreground color, height, and script manifest
     *
     * @param font       The font to use within the log pane.
     * @param background The color to use for the background.
     * @param foreground The color to use for the foreground.
     * @param height     The height of the log pane.
     * @param manifest   The manifest of the script.
     */
    public Logger(final Font font, final Color background, final Color foreground, final int height, final Manifest manifest) {
        try {
            modifiedHeight = height;
            JScrollPane scrollPane = new JScrollPane();
            frame = new JFrame();
            frame.setLayout(new BorderLayout());
            final Window parent = JFrame.getWindows()[0];

            textArea = new JTextArea();

            textArea.setFont(font);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setBackground(background);
            textArea.setForeground(foreground);
            textArea.setEditable(false);

            parentWidth = parent.getWidth();
            parentHeight = parent.getHeight();

            scrollPane.setViewportView(textArea);
            scrollPane.setPreferredSize(new Dimension(parentWidth, modifiedHeight - 20));

            //JLabel name = new JLabel("  Welcome " + Environment.getDisplayName() + "!");
            JLabel name = new JLabel("Nothing to do here.");
            name.setFont(font);
            name.setBackground(background);
            name.setForeground(foreground);

            timeLabel = new JLabel("Current time: " + DATE_FORMAT.format(Calendar.getInstance().getTime()));
            timeLabel.setFont(font);
            timeLabel.setBackground(background);
            timeLabel.setForeground(foreground);
            timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel controls = new JPanel();
            controls.setBackground(background);
            controls.setForeground(foreground);
            controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
            controls.add(name);
            controls.add(Box.createHorizontalGlue());
            controls.add(timeLabel);
            controls.setPreferredSize(new Dimension(parentWidth, 20));


            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.getContentPane().add(controls, BorderLayout.SOUTH);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            parent.setSize(parentWidth, parentHeight + height);
            parent.setMinimumSize(new Dimension(parentWidth, parentHeight + modifiedHeight));
            parent.add(frame.getContentPane(), BorderLayout.SOUTH);
            frame.pack();
            log("Successfully attached console to " + parent.getName());
            if (manifest != null)
                log("Loaded script: " + manifest.name() + " v" + manifest.version() + " by " + manifest.authors()[0]);
        } catch (Exception e) {
            log("There was an error initializing the logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs a string to the pane.
     *
     * @param s The string to log. It will be displayed as [hh:mm:ss z] Message
     */
    public static void log(String s) {
        if (textArea != null) {
            textArea.append("[" + DATE_FORMAT.format(Calendar.getInstance().getTime()) + "] " + s + System.getProperty("line.separator"));
            textArea.scrollRectToVisible(new Rectangle(0, textArea.getHeight() - 2, 1, 1));
        }
        System.out.println("[Logger]" + " [" + DATE_FORMAT.format(Calendar.getInstance().getTime()) + "] " + s);
    }

    /**
     * Removes the log pane from RSBot. Call this method on your onStop()
     * Ex: Logger.remove();
     */
    public static boolean remove() {
        final Window parent = JFrame.getWindows()[0];
        try {
            parent.remove(frame.getContentPane());
            parent.setSize(parentWidth, parentHeight - modifiedHeight);
            parent.setMinimumSize(new Dimension(parentWidth, parentHeight - modifiedHeight));
            parent.pack();
            System.out.println("Successfully removed log pane");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Updates the clock in the logger. Call this method if you want to have a functioning clock.
     */
    public static void updateTime() {
        if (timeLabel != null)
            timeLabel.setText("Current time: " + DATE_FORMAT.format(Calendar.getInstance().getTime()));
    }

    /**
     * Resize an image
     *
     * @param img The image to resize
     * @param d   The dimensions to resize to
     * @return The scaled image
     */
    public Image resize(Image img, Dimension d) {
        return img.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
    }
}
