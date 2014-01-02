package jp.dip.suitougreentea.BulletShot.state;

import jp.dip.suitougreentea.BulletShot.BulletShot;
import jp.dip.suitougreentea.BulletShot.GameBulletShot;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateLoading extends BasicGameState {
    private GameBulletShot game;

    private int stateId;
    private long starttime;
    public static final int STATUS_NULL = 0;
    public static final int STATUS_OK = 1;
    public static final int STATUS_FAIL = 2;
    public static final int STATUS_SKIPPED = 3;
    public static final int STATUS_CRITICAL = 4;
    private int[] statuses = new int[3];
    private int state;

    public StateLoading(int i) {
        stateId = i;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = (GameBulletShot) sbg;
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        starttime = Sys.getTime();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        gr.setColor(Color.white);
        gr.drawString(BulletShot.getFullProductName(), 16, 16);
        gr.setColor(Color.green);
        gr.drawString("Loading data.... Please wait.", 16, 32);
        gr.setColor(Color.white);
        gr.drawString("Fonts", 32, 64);
        gr.drawString("Graphics", 32, 80);
        gr.drawString("Init", 32, 96);
        gr.setColor(Color.green);
        for (int i = 0; i < 3; i++) {
            if (statuses[i] != 0) {
                gr.drawString("[ OK ]", 128, 64 + i * 16);
            }
        }
        // gr.drawString(, 0, 416);
        gr.setColor(Color.green);
        gr.drawString("Time : " + (Sys.getTime() - starttime), 16, 448);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        switch (state) {
        case 0:
            game.getResource().loadFont();
            statuses[0] = STATUS_OK;
            state++;
            break;
        case 1:
            game.getResource().loadImage();
            statuses[1] = STATUS_OK;
            state++;
            break;
        case 2:
            game.initStates(gc);
            statuses[2] = STATUS_OK;
            state++;
            break;
        case 3:
            sbg.enterState(1);
            break;
        default:
            throw new SlickException("Unexcepted default:.");
        }
    }

    @Override
    public int getID() {
        return stateId;
    }

}
