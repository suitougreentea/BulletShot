package jp.dip.suitougreentea.BulletShot.renderer;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.Terrain;

public abstract class RendererObject {
    protected Resource gameResource;

    public RendererObject(Resource gameResource) {
        this.gameResource = gameResource;
    }

    abstract void draw(Terrain t, int timer);
}
