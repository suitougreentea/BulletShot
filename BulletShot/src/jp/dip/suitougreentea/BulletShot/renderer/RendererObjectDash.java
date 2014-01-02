package jp.dip.suitougreentea.BulletShot.renderer;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.Terrain;

//TODO: Speed
public class RendererObjectDash extends RendererObjectNormal {
    private int speed;

    public RendererObjectDash(Resource gameResource, int direction, int speed) {
        super(gameResource, -1, direction);
        this.speed = speed;
    }

    @Override
    public void draw(Terrain t, int timer) {
        drawBase(t, (timer / 3) % 4, texturey);
    }
}
