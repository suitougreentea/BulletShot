package jp.dip.suitougreentea.BulletShot.state;

import jp.dip.suitougreentea.BulletShot.Res;
import jp.dip.suitougreentea.util.StringInput;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateEntry extends BasicGameState {

    private int stateId;

    public StateEntry(int i) { 
        stateId = i;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
            throws SlickException {
        
    }
   
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
    }

    @Override
    public int getID() {
        // TODO 自動生成されたメソッド・スタブ
        return stateId;
    }
}


