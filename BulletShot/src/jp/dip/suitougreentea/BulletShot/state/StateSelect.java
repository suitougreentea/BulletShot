package jp.dip.suitougreentea.BulletShot.state;

import jp.dip.suitougreentea.BulletShot.Res;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateSelect extends BasicGameState {

	private int stateId;

	public StateSelect(int i) {
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
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public int getID() {
		// TODO 自動生成されたメソッド・スタブ
		return stateId;
	}

}
