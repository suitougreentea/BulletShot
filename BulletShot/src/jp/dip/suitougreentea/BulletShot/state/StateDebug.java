package jp.dip.suitougreentea.BulletShot.state;

import jp.dip.suitougreentea.BulletShot.Res;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateDebug extends BasicGameState {

	private int stateId;
	private String[] menu = 
	    {
	    "CHECK MENU",
	    "I/O OPTION",
	    "DISPLAY OPTION",
	    "SOUND OPTION",
	    "GAME OPTION",
	    "STATISTICS",
	    "(UNSTABLE) DEBUG OPTION",
	    "(UNSTABLE) EXPERIMENTAL OPTION",
	    "(UNSAFE) DEEP SERVICE MENU",
	    "RETURN TO GAME"
	    };

	public StateDebug(int i) { 
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
	    Res.debugfont.draw("SERVICE / DEBUG MENU",12,12,2f);
	    Res.debugfont.drawRight("ARCADE MODE : OFF", 628, 36);
	    for(int i=0;i<menu.length;i++){
            Res.debugfont.draw(menu[i], 64, 64+i*12);
        }
	    Res.debugfont.draw("UP/DOWN : SELECT\nENTER/B1 : DECIDE\nESC/B2 : RESTART GAME", 12, 432);
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
