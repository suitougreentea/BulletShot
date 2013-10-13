package jp.dip.suitougreentea.util;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class StringInput implements KeyListener {

    private char previous;
    private String s = "";
    
    @Override
    public void setInput(Input input) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public boolean isAcceptingInput() {
        // TODO 自動生成されたメソッド・スタブ
        return true;
    }

    @Override
    public void inputEnded() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void inputStarted() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void keyPressed(int key, char c) {
        // TODO 自動生成されたメソッド・スタブ
        if(key==Input.KEY_BACK&&s.length()!=0){
            s = s.substring(0, s.length()-1);
        }
        if(c!=previous){
            if(c>=32&&c<=126)s+=c;
            previous = c;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        // TODO 自動生成されたメソッド・スタブ
        previous = 0;
    }
    
    public String getString(){
        return s;
    }
    
    public void setString(String s){
        this.s = s;
    }
}
