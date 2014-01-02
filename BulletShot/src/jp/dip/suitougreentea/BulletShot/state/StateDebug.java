package jp.dip.suitougreentea.BulletShot.state;

import jp.dip.suitougreentea.BulletShot.BulletShot;
import jp.dip.suitougreentea.BulletShot.GameBulletShot;
import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.util.BitmapFont.BitmapFont;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateDebug extends BasicGameState {
    private GameBulletShot game;
    private BitmapFont debugFont;

    private int stateId;
    private String[] menu = {"GAME MODE", "CHECK MENU", "GLOBALCONFIG", "RESTART/EXIT", };

    public StateDebug(int i) {
        stateId = i;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = (GameBulletShot) sbg;
        this.debugFont = game.getResource().getFont(Resource.FONTID_DEBUG);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        debugFont.draw("DEBUG MENU", 12, 12, 2f);
        debugFont.draw("PRODUCT: " + BulletShot.getFullProductName(), 12, 36);
        for (int i = 0; i < menu.length; i++) {
            debugFont.draw(menu[i], 24, 60 + i * 12);
        }
        debugFont.draw("UP/DOWN : SELECT\nENTER/B1 : DECIDE\nESC/B2 : RESTART GAME", 12, 432);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

    }

    @Override
    public int getID() {
        return stateId;
    }

}
