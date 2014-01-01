package jp.dip.suitougreentea.BulletShot.renderer;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Image;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.Terrain;

public class RendererObjectNormal extends RendererObject {
    private Image objTile;
    protected int texturex, texturey;

    public RendererObjectNormal(Resource gameResource, int texturex, int texturey) {
        super(gameResource);
        objTile = gameResource.getImage(Resource.IMAGEID_OBJECT_TILE);
        this.texturex = texturex;
        this.texturey = texturey;
    }

    @Override
    public void draw(Terrain t, int timer) {
        drawBase(t, texturex, texturey);
    }

    protected void drawBase(Terrain t, int texturex, int texturey) {
        objTile.bind();
        if (t.getType() == Terrain.TERRAIN_NORMAL) {
            glBegin(GL_QUADS);
            // glColor3f(1f,1f,1f);
            glNormal3f(0f, 1f, 0f);
            glTexCoord2f(0.0625f * texturex, 0.0625f * texturey);
            glVertex3f(0, 0, 0);
            glTexCoord2f(0.0625f * texturex, 0.0625f * texturey + 0.0625f);
            glVertex3f(0, 0, 1);
            glTexCoord2f(0.0625f * texturex + 0.0625f, 0.0625f * texturey + 0.0625f);
            glVertex3f(1, 0, 1);
            glTexCoord2f(0.0625f * texturex + 0.0625f, 0.0625f * texturey);
            glVertex3f(1, 0, 0);
            glEnd();
        }
    }
}
