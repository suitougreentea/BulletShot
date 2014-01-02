package jp.dip.suitougreentea.util;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class StringInput implements KeyListener {

    private char previous;
    private String s = "";

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_BACK && s.length() != 0) {
            s = s.substring(0, s.length() - 1);
        }
        if (c != previous) {
            if (c >= 32 && c <= 126) {
                s += c;
            }
            previous = c;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        previous = 0;
    }

    public String getString() {
        return s;
    }

    public void setString(String s) {
        this.s = s;
    }
}
