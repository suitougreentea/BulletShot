package jp.dip.suitougreentea.BulletShot.renderer;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

import jp.dip.suitougreentea.BulletShot.Res;
import jp.dip.suitougreentea.BulletShot.Terrain;

public class RendererObjectNormal{
    public RendererObjectNormal(){
        
    }
    public void draw(Terrain t){
        Res.objDash.bind();
        glBegin(GL_QUADS);
        //glColor3f(1f,1f,1f);
        glNormal3f(0f,1f,0f);
        glTexCoord2f(0,0);
        glVertex3f(0,0,0);
        glTexCoord2f(0,0.0625f);
        glVertex3f(0,0,1);
        glTexCoord2f(0.0625f,0.0625f);
        glVertex3f(1,0,1);
        glTexCoord2f(0.0625f,0);
        glVertex3f(1,0,0);
        glEnd();
        
        TextureImpl.bindNone();
        //glDisable(GL_LIGHTING);
        glBlendFunc(GL_ZERO, GL_SRC_COLOR);
        glBegin(GL_QUADS);
        glColor4f(1f,1f,0f,0.5f);
        glNormal3f(0f,1f,0f);
        glVertex3f(0.125f,0.005f,0.125f);
        glVertex3f(0.125f,0.005f,0.875f);
        glVertex3f(0.875f,0.005f,0.875f);
        glVertex3f(0.875f,0.005f,0.125f);
        glEnd();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LIGHTING);
    }
}
