package jp.dip.suitougreentea.BulletShot.renderer;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

import jp.dip.suitougreentea.BulletShot.Res;
import jp.dip.suitougreentea.BulletShot.Terrain;

public class RendererObjectNormal implements RendererObject {
    int texturex,texturey;
    
    public RendererObjectNormal(int texturex, int texturey){
        this.texturex = texturex;
        this.texturey = texturey;
    }
    
    public void draw(Terrain t, int timer){
       drawBase(t, texturex, texturey);
    }
    
    protected void drawBase(Terrain t, int texturex, int texturey){
        Res.objTile.bind();
        if(t.getType()==Terrain.TERRAIN_NORMAL){
            glBegin(GL_QUADS);
            //glColor3f(1f,1f,1f);
            glNormal3f(0f,1f,0f);
            glTexCoord2f(0.0625f*texturex,0.0625f*texturey);
            glVertex3f(0,0,0);
            glTexCoord2f(0.0625f*texturex,0.0625f*texturey+0.0625f);
            glVertex3f(0,0,1);
            glTexCoord2f(0.0625f*texturex+0.0625f,0.0625f*texturey+0.0625f);
            glVertex3f(1,0,1);
            glTexCoord2f(0.0625f*texturex+0.0625f,0.0625f*texturey);
            glVertex3f(1,0,0);
        glEnd();
        }
    }
}
