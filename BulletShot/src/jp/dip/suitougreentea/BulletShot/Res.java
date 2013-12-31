package jp.dip.suitougreentea.BulletShot;

import java.io.IOException;

import jp.dip.suitougreentea.util.BitmapFont.BitmapFont;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Res {
    public static BitmapFont font;
    public static BitmapFont debugfont;
    public static BitmapFont steiner;
    public static BitmapFont debugfontsmall;
    public static BitmapFont impact;
    
    public static Image title;
    
    public static Image tile;
    public static Image wall;
    public static Image wleft;
    public static Image wright;
    public static Image dback;
    public static Image versionstate;
    public static Image sun;
    public static Image gui;
    public static Image guishoot;
    
    public static Image sky;
    public static Image cloudssmall,cloudsmedium,cloudslarge;
    
    public static Image objTile;
    
    public static void load() throws SlickException {
        font = loadBitmapFont("main");
        //font.setDrawShadow(false);
        font.setPaddingX(-1);
        font.setPaddingY(-1);
        debugfont = loadBitmapFont("12x8-1x2-0");
        //debugfont.setDrawShadow(false);
        //debugfont.setPaddingX(4);
        debugfontsmall = loadBitmapFont("debugsmall");
        steiner = loadBitmapFont("steiner");
        steiner.setPaddingX(-1);
        impact = loadBitmapFont("Impact");
        impact.setPaddingX(-3);
        
        title = loadImage("title.png");
        
        tile = loadImage("tile.png");
        //tile.setFilter(Image.FILTER_NEAREST);
        wall = loadImage("wall.png");
        wall.setFilter(Image.FILTER_NEAREST);
        wleft = loadImage("wallleft.png");
        wleft.setFilter(Image.FILTER_NEAREST);
        wright = loadImage("wallright.png");
        wright.setFilter(Image.FILTER_NEAREST);
        dback = loadImage("backgroundm3.png");
        //versionstate = loadImage("m1.png");
        //sun = loadImage("sun.png");
        gui = loadImage("gui.png");
        guishoot = loadImage("guishoot.png");
    }
    
    public static void loadObjectTexture() throws SlickException {
        objTile = loadImage("object/tile.png");
        objTile.setFilter(Image.FILTER_NEAREST);
    }
    
    public static Image loadImage(String path) {
        Image img = null;
        try {
            img = new Image("res/image/"+path);
        } catch (SlickException e) {
            System.out.println("Failed to load image:"+path);
        }
        return img;
    }
    
    static Image loadImageRes(String path) {
        Image img = null;
        try {
            img = new Image("res/"+path);
        } catch (SlickException e) {
            System.out.println("Failed to load image:"+path);
        }
        return img;
    }
    
    static BitmapFont loadBitmapFont(String name) {
        BitmapFont font = null;
        //Image img = loadImageRes("font/"+name+".png");
        try {
            font = new BitmapFont("res/font/" + name);
        } catch (IOException e) {
            System.out.println("Failed to load font:"+name);
        } catch (SlickException e) {
            System.out.println("Failed to load font:"+name);
        }
        return font;
    }
}
