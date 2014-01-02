package jp.dip.suitougreentea.BulletShot.renderer;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Image;

import jp.dip.suitougreentea.BulletShot.Resource;

public class GUIRenderer {
    @SuppressWarnings("unused")
    private Resource gameResource;
    private Image gui;

    public GUIRenderer(Resource gameResource) {
        this.gameResource = gameResource;
        this.gui = gameResource.getImage(Resource.IMAGEID_GUI_MAIN);
    }

    public void draw() {
        glPushMatrix();
        glTranslatef(0, 384, 0);
        drawContainer(640);
        glPopMatrix();

        // Res.gui.draw();
    }

    private void drawContainer(int width) {
        gui.draw(0, 0, 8, 64, 0, 0, 8, 64); // left
        gui.draw(8, 0, width - 8, 64, 8, 0, 16, 64); // center
        gui.draw(width - 8, 0, width, 64, 16, 0, 24, 64); // right
    }
}
