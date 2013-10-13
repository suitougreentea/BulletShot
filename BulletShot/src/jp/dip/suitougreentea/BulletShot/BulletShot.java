package jp.dip.suitougreentea.BulletShot;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class BulletShot {
	private static Logger logger;
	
    public static void main(String[] args) throws SlickException{
        logger = Logger.getLogger("jp.dip.suitougreentea.BulletShot.defaultLogger");
        logger.setUseParentHandlers(false);
        logger.addHandler(new LoggerHandler());
        logger.setLevel(Level.ALL);
        logger.info(getVersion());

        try {
    		AppGameContainer app = new AppGameContainer(new GameBulletShot(getVersion(),(args.length > 0&&args[0].equals("true"))?true:false));
    		
    		app.setDisplayMode(640,480, false);
    		
    		app.setShowFPS(false);
    
    		app.setTargetFrameRate(60);
    		
    		app.start();
    		
        } catch (SlickException e){
            e.printStackTrace();
            JFrame errorscreen = new FrameErrorScreen("I'm sorry, but an unexcepted error occured.");
            errorscreen.setVisible(true);
        }
	}
	public static String getVersion(){
	    return 
	            "BulletShot M3 (OBJECT / GAMEPLAY TEST) - 0.3.02";
	}
    public static String getVersionShorter(){
        return 
                "BulletShot M3";
    }
	public static Logger getLogger(){
	    return logger;
	}
	public static void drawLog(){
	    ((LoggerHandler)(BulletShot.getLogger().getHandlers()[0])).drawRecords();
	}
}
