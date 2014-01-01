package jp.dip.suitougreentea.BulletShot;

import java.io.IOException;

import jp.dip.suitougreentea.util.BitmapFont.BitmapFont;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Resource {
    private String baseDir;

    private BitmapFont[] font = new BitmapFont[5];
    //public static final int FONTID_MAIN = 0;
    public static final int FONTID_DEBUG = 1;
    public static final int FONTID_DEBUG_SMALL = 2;
    //public static final int FONTID_STEINER = 3;
    //public static final int FONTID_IMPACT = 4;

    private Image[] image = new Image[9];
    public static final int IMAGEID_TITLE = 0;
    public static final int IMAGEID_WALL = 1;
    public static final int IMAGEID_WALL_LEFT = 2;
    public static final int IMAGEID_WALL_RIGHT = 3;
    public static final int IMAGEID_BACKGROUND_DEV = 4;
    public static final int IMAGEID_GUI_MAIN = 5;
    public static final int IMAGEID_GUI_SHOOT = 6;
    public static final int IMAGEID_OBJECT_TILE = 7;
    public static final int IMAGEID_TILE = 8;

    public Resource(String baseDir) {
        this.baseDir = baseDir;
    }

    @Deprecated
    public void load() throws SlickException {
        loadFont();
        loadImage();
        loadMusic();
        loadSound();
    }

    public void loadFont() throws SlickException {
        font[FONTID_DEBUG] = loadFontData("font/12x8-1x2-0");
        font[FONTID_DEBUG_SMALL] = loadFontData("font/debugsmall");
    }

    public void loadImage() throws SlickException {
        image[IMAGEID_TITLE] = loadImageData("image/title.png");
        image[IMAGEID_WALL] = loadImageData("image/wall.png");
        image[IMAGEID_WALL].setFilter(Image.FILTER_NEAREST);
        image[IMAGEID_WALL_LEFT] = loadImageData("image/wallleft.png");
        image[IMAGEID_WALL_LEFT].setFilter(Image.FILTER_NEAREST);
        image[IMAGEID_WALL_RIGHT] = loadImageData("image/wallright.png");
        image[IMAGEID_WALL_RIGHT].setFilter(Image.FILTER_NEAREST);
        image[IMAGEID_BACKGROUND_DEV] = loadImageData("image/backgroundm3.png");
        image[IMAGEID_GUI_MAIN] = loadImageData("image/gui.png");
        image[IMAGEID_GUI_SHOOT] = loadImageData("image/guishoot.png");
        image[IMAGEID_OBJECT_TILE] = loadImageData("image/object/tile.png");
        image[IMAGEID_OBJECT_TILE].setFilter(Image.FILTER_NEAREST);
        image[IMAGEID_TILE] = loadImageData("image/tile.png");
        image[IMAGEID_TILE].setFilter(Image.FILTER_NEAREST);
    }

    public void loadMusic() throws SlickException {

    }

    public void loadSound() throws SlickException {

    }

    public BitmapFont loadFontData(String relativePath) throws SlickException {
        BitmapFont font = null;
        try {
            font = new BitmapFont(baseDir + "/" + relativePath);
        } catch (IOException e) {
            throw new SlickException("Failed to load font:" + relativePath);
        } catch (SlickException e) {
            throw new SlickException("Failed to load font:" + relativePath);
        }
        return font;
    }

    public Image loadImageData(String relativePath) throws SlickException {
        Image img = null;
        try {
            img = new Image(baseDir + "/" + relativePath);
        } catch (SlickException e) {
            throw new SlickException("Failed to load image:" + relativePath);
        }
        return img;
    }

    public BitmapFont getFont(int id) {
        return font[id];
    }

    public Image getImage(int id) {
        return image[id];
    }
}
