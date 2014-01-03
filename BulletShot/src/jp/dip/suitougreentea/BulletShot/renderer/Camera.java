package jp.dip.suitougreentea.BulletShot.renderer;

import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.util.glu.GLU;

public class Camera {
    private float cameraX, cameraY, cameraZ;
    private float zoom;
    private float mode;

    private int displayWidth, displayHeight;

    public Camera(int displayWidth, int displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.zoom = 5f;
    }

    /*public void followCamera(float targetX, float targetY, float targetZ) {

    }*/

    public void updateCamera() {
        final float h = (float) displayWidth / (float) (displayHeight);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-zoom * h, zoom * h, -zoom, zoom, -5, 30);
        if (mode == 0) {
            GLU.gluLookAt(10f + cameraX, 12f, 10f + cameraZ, 0.0f + cameraX, 0.0f, 0.0f + cameraZ, 0.0f, 1.0f, 0.0f);
        }
        if (mode == 1) {
            GLU.gluLookAt(10f + cameraX, 12f, -10f + cameraZ, 0.0f + cameraX, 0.0f, 0.0f + cameraZ, 0.0f, 1.0f, 0.0f);
        }
        if (mode == 2) {
            GLU.gluLookAt(-10f + cameraX, 12f, -10f + cameraZ, 0.0f + cameraX, 0.0f, 0.0f + cameraZ, 0.0f, 1.0f, 0.0f);
        }
        if (mode == 3) {
            GLU.gluLookAt(-10f + cameraX, 12f, 10f + cameraZ, 0.0f + cameraX, 0.0f, 0.0f + cameraZ, 0.0f, 1.0f, 0.0f);
        }

        // GLU.gluLookAt(0,2,0, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);//シャドウマッピング用
    }

    public void cameraIn() {
        zoom += 0.05f;
    }

    public void cameraOut() {
        zoom -= 0.05f;
    }

    public void changeCameraPos() {
        mode = (mode + 1) % 4;
    }

    public void moveLeft() {
        cameraX -= 0.07f;
        cameraZ += 0.07f;
    }

    public void moveRight() {
        cameraX += 0.07f;
        cameraZ -= 0.07f;
    }

    public void moveFront() {
        cameraX -= 0.07f;
        cameraZ -= 0.07f;
    }

    public void moveBack() {
        cameraX += 0.07f;
        cameraZ += 0.07f;
    }

    public float getCameraX() {
        return cameraX;
    }

    public void setCameraX(float cameraPosX) {
        this.cameraX = cameraPosX;
    }

    public float getCameraZ() {
        return cameraZ;
    }

    public void setCameraZ(float cameraPosZ) {
        this.cameraZ = cameraPosZ;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
