package jp.dip.suitougreentea.BulletShot;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public final class BulletShot {
    private BulletShot() {

    }

    // TODO: 三項演算子の使用について
    public static final Logger LOGGER = Logger.getLogger("jp.dip.suitougreentea.BulletShot.defaultLogger");

    public static final String PRODUCT_NAME = "BulletShot";
    public static final String VERSION = "M3";
    public static final String DEV_VERSION = "(OBJECT / GAMEPLAY TEST) - 0.3.02";

    public static void main(String[] args) throws SlickException {
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(new LoggerHandler());
        LOGGER.setLevel(Level.ALL);
        LOGGER.info(getFullProductName());
        try {
            AppGameContainer app = new AppGameContainer(new GameBulletShot(getFullProductName(), (args.length > 0 && args[0].equals("true")) ? true : false));
            app.setDisplayMode(640, 480, false);
            app.setShowFPS(false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
            JFrame errorscreen = new FrameErrorScreen("I'm sorry, but an unexcepted error occured.");
            errorscreen.setVisible(true);
        }
    }

    public static String getFullProductName() {
        return PRODUCT_NAME + " " + VERSION + " " + DEV_VERSION;
    }

    public static String getShorterProductName() {
        return PRODUCT_NAME + " " + VERSION;
    }
}
