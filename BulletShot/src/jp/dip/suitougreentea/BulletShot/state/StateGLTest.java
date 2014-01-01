package jp.dip.suitougreentea.BulletShot.state;

import static org.lwjgl.opengl.GL11.*;

import jp.dip.suitougreentea.BulletShot.Res;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGLTest extends BasicGameState {

    private int stateId;

    public StateGLTest(int i) {
        stateId = i;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        glClearColor(0.0f, 0.0f, 0.0f, 1f);
        glClearDepth(1.0f);

        glDepthFunc(GL_LEQUAL);

        updateCamera();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void updateCamera() {
        final float h = (float) 640 / (float) (/* 50+ */480); // TODO:縦横比やっつけ調整
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float zoom = 10;
        glOrtho(-h * zoom, h * zoom, -zoom, zoom, -500, 300);
        GLU.gluPerspective(90, h, -500, 300);
        GLU.gluLookAt(10f, 12f, 10f, -50.0f, 0.0f, -50.0f, 0.0f, 1.0f, 0.0f);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        // Res.sky.draw();

        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();

        updateCamera();

        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();

        glLoadIdentity();

        TextureImpl.bindNone();
        glColor3f(1f, 1f, 1f);
        new Sphere().draw(1f, 20, 20);

        Res.wall.draw();
        // glBegin()

        glDisable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        // Res.gui.draw();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

    }

    void drawQuad() {
        glEnable(GL_TEXTURE_2D);
        Res.tile.bind();
        glBegin(GL11.GL_QUADS);
        glColor3f(1f, 1f, 1f);
        glTexCoord2f(0, 0);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 0);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    @Override
    public int getID() {
        return stateId;
    }

}
