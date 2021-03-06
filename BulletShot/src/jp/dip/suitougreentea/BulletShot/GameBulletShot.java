package jp.dip.suitougreentea.BulletShot;

import jp.dip.suitougreentea.BulletShot.state.StateDebug;
import jp.dip.suitougreentea.BulletShot.state.StateEdit;
import jp.dip.suitougreentea.BulletShot.state.StateEntry;
import jp.dip.suitougreentea.BulletShot.state.StateGLTest;
import jp.dip.suitougreentea.BulletShot.state.StateGame;
import jp.dip.suitougreentea.BulletShot.state.StateLoading;
import jp.dip.suitougreentea.BulletShot.state.StateSelect;
import jp.dip.suitougreentea.BulletShot.state.StateTitle;
import jp.dip.suitougreentea.util.BitmapFont.BitmapFont;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameBulletShot extends StateBasedGame {
    private Resource resource = new Resource("res");
    private BitmapFont debugFont;

    private boolean stateselect = false;

    public GameBulletShot(String name, boolean stateselect) {
        super(name);
        this.stateselect = stateselect;

        this.addState(new StateLoading(0));
        this.addState(new StateGame(1));
        this.addState(new StateTitle(2));
        this.addState(new StateGLTest(3));
        this.addState(new StateDebug(4));
        this.addState(new StateEntry(5));
        this.addState(new StateSelect(6));
        this.addState(new StateEdit(7));

        this.enterState(0);
    }

    //StateLoadingのみ
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(0).init(gc, this);
    }

    //StateLoadingから呼び出される、他のStateの初期化
    public void initStates(GameContainer gc) throws SlickException {
        this.getState(1).init(gc, this);
        this.getState(2).init(gc, this);
        this.getState(3).init(gc, this);
        this.getState(4).init(gc, this);
        this.getState(5).init(gc, this);
        this.getState(6).init(gc, this);
        this.getState(7).init(gc, this);

        this.debugFont = resource.getFont(Resource.FONTID_DEBUG);
    }

    @Override
    protected void postRenderState(GameContainer container, Graphics g) throws SlickException {
        if (this.getCurrentStateID() != 4 && this.getCurrentStateID() != -1 && this.getCurrentStateID() != 0) {
            debugFont.draw("FPS:" + String.valueOf(container.getFPS()), 16, 16);
            debugFont.drawRight(String.format("Mem:%.1f/%.1f(%.1f)", Runtime.getRuntime().freeMemory() / 1048576f, Runtime.getRuntime().totalMemory() / 1048576f, Runtime.getRuntime().maxMemory() / 1048576f), 624, 16);
            debugFont.draw("Offline", 16, 452);
            debugFont.drawRight(BulletShot.getShorterProductName(), 624, 452);
            ((LoggerHandler) (BulletShot.LOGGER.getHandlers()[0])).drawRecords(resource);
        }
    }

    @Override
    protected void preUpdateState(GameContainer container, int delta) {
        if (stateselect) {
            Input i = container.getInput();

            if (i.isKeyDown(Input.KEY_RSHIFT)) {
                if (i.isKeyPressed(Input.KEY_1)) {
                    this.enterState(1);
                }
                if (i.isKeyPressed(Input.KEY_2)) {
                    this.enterState(2);
                }
                if (i.isKeyPressed(Input.KEY_3)) {
                    this.enterState(3);
                }
                if (i.isKeyPressed(Input.KEY_4)) {
                    this.enterState(4);
                }
                if (i.isKeyPressed(Input.KEY_5)) {
                    this.enterState(5);
                }
                if (i.isKeyPressed(Input.KEY_6)) {
                    this.enterState(6);
                }
                if (i.isKeyPressed(Input.KEY_7)) {
                    this.enterState(7);
                }
            }
        }
    }

    @Override
    protected void postUpdateState(GameContainer container, int delta) {
        container.getInput().clearKeyPressedRecord();
    }

    public Resource getResource() {
        return resource;
    }
}
